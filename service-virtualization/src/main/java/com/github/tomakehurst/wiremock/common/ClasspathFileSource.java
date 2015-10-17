package com.github.tomakehurst.wiremock.common;

import static com.github.tomakehurst.wiremock.common.Exceptions.throwUnchecked;
import static com.google.common.base.MoreObjects.firstNonNull;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Iterators.find;
import static com.google.common.collect.Iterators.forEnumeration;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Thread.currentThread;
import static java.util.Arrays.asList;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterators;
import com.google.common.io.Resources;

public class ClasspathFileSource implements FileSource {

  private final String path;
  private ZipFile zipFile;
  private File rootDirectory;

  public ClasspathFileSource(final String path) {
    this.path = path;

    try {
      URL resource =
          firstNonNull(currentThread().getContextClassLoader(), Resources.class.getClassLoader())
              .getResource(path);
      if (resource == null) {
        rootDirectory = new File(path);
        return;
      }

      URI pathUri = resource.toURI();

      if (asList("jar", "war", "ear", "zip").contains(pathUri.getScheme())) {
        String jarFileUri = pathUri.getSchemeSpecificPart().split("!")[0];
        File file = new File(URI.create(jarFileUri));
        zipFile = new ZipFile(file);
      } else if (pathUri.getScheme().equals("file")) {
        rootDirectory = new File(pathUri);
      } else {
        throw new RuntimeException("ClasspathFileSource can't handle paths of type "
            + pathUri.getScheme());
      }

    } catch (Exception e) {
      throwUnchecked(e);
    }
  }

  private boolean isFileSystem() {
    return rootDirectory != null;
  }

  public BinaryFile getBinaryFileNamed(final String name) {
    if (isFileSystem()) {
      return new BinaryFile(new File(rootDirectory, name).toURI());
    }

    ZipEntry zipEntry = find(forEnumeration(zipFile.entries()), new Predicate<ZipEntry>() {
      public boolean apply(final ZipEntry input) {
        return input.getName().equals(path + "/" + name);
      }
    });

    return new BinaryFile(getUriFor(zipEntry));
  }

  public void createIfNecessary() {
    throw new UnsupportedOperationException("Classpath file sources are read-only");
  }

  public FileSource child(final String subDirectoryName) {
    return new ClasspathFileSource(path + "/" + subDirectoryName);
  }

  public String getPath() {
    return path;
  }

  public List<TextFile> listFilesRecursively() {
    if (isFileSystem()) {
      assertExistsAndIsDirectory();
      List<File> fileList = newArrayList();
      recursivelyAddFilesToList(rootDirectory, fileList);
      return toTextFileList(fileList);
    }

    return FluentIterable.from(toIterable(zipFile.entries())).filter(new Predicate<ZipEntry>() {
      public boolean apply(final ZipEntry jarEntry) {
        return !jarEntry.isDirectory() && jarEntry.getName().startsWith(path);
      }
    }).transform(new Function<ZipEntry, TextFile>() {
      public TextFile apply(final ZipEntry jarEntry) {
        return new TextFile(getUriFor(jarEntry));
      }
    }).toList();
  }

  private URI getUriFor(final ZipEntry jarEntry) {
    try {
      return Resources.getResource(jarEntry.getName()).toURI();
    } catch (URISyntaxException e) {
      return throwUnchecked(e, URI.class);
    }
  }

  private void recursivelyAddFilesToList(final File root, final List<File> fileList) {
    File[] files = root.listFiles();
    for (File file : files) {
      if (file.isDirectory()) {
        recursivelyAddFilesToList(file, fileList);
      } else {
        fileList.add(file);
      }
    }
  }

  private List<TextFile> toTextFileList(final List<File> fileList) {
    return newArrayList(transform(fileList, new Function<File, TextFile>() {
      public TextFile apply(final File input) {
        return new TextFile(input.toURI());
      }
    }));
  }

  public void writeTextFile(final String name, final String contents) {
    throw new UnsupportedOperationException("Classpath file sources are read-only");
  }

  public void writeBinaryFile(final String name, final byte[] contents) {
    throw new UnsupportedOperationException("Classpath file sources are read-only");
  }

  public boolean exists() {
    // It'll only be non-file system if finding the classpath resource
    // succeeded in the constructor
    return (isFileSystem() && rootDirectory.exists()) || (!isFileSystem());
  }

  private static <T> Iterable<T> toIterable(final Enumeration<T> e) {
    return new Iterable<T>() {

      public Iterator<T> iterator() {
        return Iterators.forEnumeration(e);
      }
    };
  }

  private void assertExistsAndIsDirectory() {
    if (rootDirectory.exists() && !rootDirectory.isDirectory()) {
      throw new RuntimeException(rootDirectory + " is not a directory");
    } else if (!rootDirectory.exists()) {
      throw new RuntimeException(rootDirectory + " does not exist");
    }
  }
}
