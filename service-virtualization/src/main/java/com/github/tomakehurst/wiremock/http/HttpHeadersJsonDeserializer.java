package com.github.tomakehurst.wiremock.http;

import static com.google.common.collect.Iterables.transform;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;

public class HttpHeadersJsonDeserializer extends JsonDeserializer<HttpHeaders> {

  @Override
  public HttpHeaders deserialize(final JsonParser parser, final DeserializationContext context)
      throws IOException {
    JsonNode rootNode = parser.readValueAsTree();
    return new HttpHeaders(transform(all(rootNode.fields()), toHttpHeaders()));
  }

  private static Function<Map.Entry<String, JsonNode>, HttpHeader> toHttpHeaders() {
    return new Function<Map.Entry<String, JsonNode>, HttpHeader>() {

      public HttpHeader apply(final Map.Entry<String, JsonNode> field) {
        String key = field.getKey();
        if (field.getValue().isArray()) {
          return new HttpHeader(key, ImmutableList.copyOf(transform(
              all(field.getValue().elements()), toStringValues())));
        } else {
          return new HttpHeader(key, field.getValue().textValue());
        }
      }
    };
  }

  private static Function<JsonNode, String> toStringValues() {
    return new Function<JsonNode, String>() {
      public String apply(final JsonNode node) {
        return node.textValue();
      }
    };
  }

  public static <T> Iterable<T> all(final Iterator<T> underlyingIterator) {
    return new Iterable<T>() {
      public Iterator<T> iterator() {
        return underlyingIterator;
      }
    };
  }
}
