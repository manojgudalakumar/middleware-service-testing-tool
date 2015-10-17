package com.github.tomakehurst.wiremock.extension;

import static com.github.tomakehurst.wiremock.common.Exceptions.throwUnchecked;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.FluentIterable.from;
import static java.util.Arrays.asList;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class ExtensionLoader {

  @SuppressWarnings("unchecked")
  public static <T> Map<String, T> loadExtension(final String... classNames) {
    return (Map<String, T>) asMap(from(asList(classNames)).transform(toClasses()).transform(
        toExtensions()));
  }

  public static Map<String, Extension> load(final String... classNames) {
    return loadExtension(classNames);
  }

  public static Map<String, Extension> asMap(final Iterable<Extension> extensions) {
    return Maps.uniqueIndex(extensions, new Function<Extension, String>() {
      public String apply(final Extension extension) {
        return extension.name();
      }
    });
  }

  public static Map<String, Extension> load(final Class<? extends Extension>... classes) {
    return asMap(from(asList(classes)).transform(toExtensions()));
  }

  private static Function<Class<? extends Extension>, Extension> toExtensions() {
    return new Function<Class<? extends Extension>, Extension>() {
      public Extension apply(final Class<? extends Extension> extensionClass) {
        try {
          checkArgument(ResponseTransformer.class.isAssignableFrom(extensionClass),
              "Extension classes must implement ResponseTransformer");
          return extensionClass.newInstance();
        } catch (Exception e) {
          return throwUnchecked(e, Extension.class);
        }

      }
    };
  }

  private static Function<String, Class<? extends Extension>> toClasses() {
    return new Function<String, Class<? extends Extension>>() {
      @SuppressWarnings("unchecked")
      public Class<? extends Extension> apply(final String className) {
        try {
          return (Class<? extends Extension>) Class.forName(className);
        } catch (ClassNotFoundException e) {
          return throwUnchecked(e, Class.class);
        }
      }
    };
  }
}
