package com.github.malkomich.scrapping.tools.domain;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public class ScrappingRequest {

    private String url;
    private String containerSelector;
    private Collection<Field> fields;

    public ScrappingRequest(final JsonObject json) {
        this.url = json.getString("url");
        this.containerSelector = json.getString("containerSelector");
        this.fields = fieldsFromJson(json);
        checkParameters(url, containerSelector, fields);
    }

    public ScrappingRequest(final String url, final String containerSelector, final Collection<Field> fields) {
        this.url = url;
        this.containerSelector = containerSelector;
        this.fields = fields;
        checkParameters(url, containerSelector, fields);
    }

    public static ScrappingRequestBuilder builder() {
        return new ScrappingRequestBuilder();
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

    private void checkParameters(final String url, final String containerSelector, final Collection<Field> fields) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(containerSelector)) {
            throw new IllegalArgumentException("URL or selectors cannot be empty");
        }
        if (fields == null || fields.isEmpty()) {
            throw new IllegalArgumentException("Fields not provided");
        }
    }

    private Collection<Field> fieldsFromJson(final JsonObject json) {
        return json.getJsonArray("fields", new JsonArray())
            .stream()
            .map(JsonObject.class::cast)
            .map(Field::new)
            .collect(Collectors.toList());
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
