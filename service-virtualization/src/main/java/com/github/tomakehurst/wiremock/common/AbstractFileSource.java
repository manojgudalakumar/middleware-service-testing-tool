package com.github.tomakehurst.wiremock.common;

import static com.google.common.base.Charsets.UTF_8;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.io.Files;

public abstract class AbstractFileSource implements FileSource {

  protected final File rootDirectory;

  public AbstractFileSource(final File rootDirectory) {
    this.rootDirectory = rootDirectory;
  }

  protected abstract boolean readOnly();

  public BinaryFile getBinaryFileNamed(final String name) {
    return new BinaryFile(new File(rootDirectory, name).toURI());
  }

  public void createIfNecessary() {
    assertWritable();
    if (rootDirectory.exists() && rootDirectory.isFile()) {
      throw new IllegalStateException(rootDirectory + " already exists and is a file");
    } else if (!rootDirectory.exists()) {
      rootDirectory.mkdirs();
    }
  }

  public String getPath() {
    return rootDirectory.getPath();
  }

  public List<TextFile> listFilesRecursively() {
    assertExistsAndIsDirectory();
    List<File> fileList = newArrayList();
    recursivelyAddFilesToList(rootDirectory, fileList);
    return toTextFileList(fileList);
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
        System.out.println(input.toPath());
        return new TextFile(input.toURI());
      }
    }));
  }

  public void writeTextFile(final String name, final String contents) {
    writeTextFileAndTranslateExceptions(contents, writableFileFor(name));
  }

  public void writeBinaryFile(final String name, final byte[] contents) {
    writeBinaryFileAndTranslateExceptions(contents, writableFileFor(name));
  }

  public boolean exists() {
    return rootDirectory.exists();
  }

  private File writableFileFor(final String name) {
    assertExistsAndIsDirectory();
    assertWritable();
    return new File(rootDirectory, name);
  }

  private void assertExistsAndIsDirectory() {
    if (rootDirectory.exists() && !rootDirectory.isDirectory()) {
      throw new RuntimeException(rootDirectory + " is not a directory");
    } else if (!rootDirectory.exists()) {
      throw new RuntimeException(rootDirectory + " does not exist");
    }
  }

  private void assertWritable() {
    if (readOnly()) {
      throw new UnsupportedOperationException("Can't write to read only file sources");
    }
  }

  private void writeTextFileAndTranslateExceptions(final String contents, final File toFile) {
    try {
      Files.write(contents, toFile, UTF_8);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

  private void writeBinaryFileAndTranslateExceptions(final byte[] contents, final File toFile) {
    try {
      Files.write(contents, toFile);
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }

}
