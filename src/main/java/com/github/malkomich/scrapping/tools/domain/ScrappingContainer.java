package com.github.malkomich.scrapping.tools.domain;

import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScrappingContainer {
    private Map<String, ScrappedValue> fields;

    ScrappingContainer(final JsonObject json) {
        this.fields = Optional.ofNullable(json.getJsonObject("fields"))
            .map(JsonObject::getMap)
            .map(Map::entrySet)
            .orElse(Collections.emptySet())
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, o -> new ScrappedValue<>(o.getValue())));
    }

    public ScrappingContainer(final Map<String, ScrappedValue> fields) {
        this.fields = fields;
    }

    public static ScrappingContainerBuilder builder() {
        return new ScrappingContainerBuilder();
    }

    public Map<String, ScrappedValue> getFields() {
        return this.fields;
    }

    public static class ScrappingContainerBuilder {
        private Map<String, ScrappedValue> fields;

        ScrappingContainerBuilder() {
        }

        public ScrappingContainer.ScrappingContainerBuilder fields(final Map<String, ScrappedValue> fields) {
            this.fields = fields;
            return this;
        }

        public ScrappingContainer build() {
            return new ScrappingContainer(fields);
        }

        public String toString() {
            return "ScrappingContainer.ScrappingContainerBuilder(fields=" + this.fields + ")";
        }
    }
}
