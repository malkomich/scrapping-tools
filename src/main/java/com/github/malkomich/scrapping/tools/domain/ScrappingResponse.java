package com.github.malkomich.scrapping.tools.domain;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class ScrappingResponse {
    private List<ScrappingContainer> containers;

    public ScrappingResponse(final JsonObject json) {
        this.containers = json.getJsonArray("containers", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(ScrappingContainer::new)
            .collect(Collectors.toList());
    }

    public ScrappingResponse(final List<ScrappingContainer> containers) {
        this.containers = containers;
    }

    public static ScrappingResponseBuilder builder() {
        return new ScrappingResponseBuilder();
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

    public boolean isEmpty() {
        return containers.isEmpty();
    }

    public List<ScrappingContainer> getContainers() {
        return this.containers;
    }

    public static class ScrappingResponseBuilder {
        private List<ScrappingContainer> containers;

        ScrappingResponseBuilder() {
        }

        public ScrappingResponse.ScrappingResponseBuilder containers(final List<ScrappingContainer> containers) {
            this.containers = containers;
            return this;
        }

        public ScrappingResponse build() {
            return new ScrappingResponse(containers);
        }

        public String toString() {
            return "ScrappingResponse.ScrappingResponseBuilder(containers=" + this.containers + ")";
        }
    }
}
