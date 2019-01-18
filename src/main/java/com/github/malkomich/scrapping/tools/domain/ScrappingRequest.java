package com.github.malkomich.scrapping.tools.domain;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Collection;
import java.util.stream.Collectors;

public class ScrappingRequest {

    private String url;
    private String containerSelector;
    private Collection<Field> fields;

    public ScrappingRequest(final JsonObject json) {
        this.url = json.getString("url");
        this.containerSelector = json.getString("containerSelector");
        this.fields = getFields(json);
    }

    public ScrappingRequest(final String url, final String containerSelector, final Collection<Field> fields) {
        this.url = url;
        this.containerSelector = containerSelector;
        this.fields = fields;
    }

    public static ScrappingRequestBuilder builder() {
        return new ScrappingRequestBuilder();
    }

    private Collection<Field> getFields(final JsonObject json) {
        return json.getJsonArray("fields", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(Field::new)
            .collect(Collectors.toList());
    }

    public JsonObject toJson() {
        return JsonObject.mapFrom(this);
    }

    public String getUrl() {
        return this.url;
    }

    public String getContainerSelector() {
        return this.containerSelector;
    }

    public Collection<Field> getFields() {
        return this.fields;
    }

    public static class ScrappingRequestBuilder {
        private String url;
        private String containerSelector;
        private Collection<Field> fields;

        ScrappingRequestBuilder() {
        }

        public ScrappingRequest.ScrappingRequestBuilder url(final String url) {
            this.url = url;
            return this;
        }

        public ScrappingRequest.ScrappingRequestBuilder containerSelector(final String containerSelector) {
            this.containerSelector = containerSelector;
            return this;
        }

        public ScrappingRequest.ScrappingRequestBuilder fields(final Collection<Field> fields) {
            this.fields = fields;
            return this;
        }

        public ScrappingRequest build() {
            return new ScrappingRequest(url, containerSelector, fields);
        }

        public String toString() {
            return "ScrappingRequest.ScrappingRequestBuilder(url=" + this.url
                    + ", containerSelector=" + this.containerSelector
                    + ", fields=" + this.fields + ")";
        }
    }
}
