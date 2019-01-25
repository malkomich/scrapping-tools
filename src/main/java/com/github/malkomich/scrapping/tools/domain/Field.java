package com.github.malkomich.scrapping.tools.domain;

import io.vertx.core.json.JsonObject;

public class Field {
    private String selector;
    private String name;
    private Type type;

    Field(final JsonObject json) {
        this.selector = json.getString("selector");
        this.name = json.getString("name");
        this.type = Type.valueOf(json.getString("type"));
    }

    public Field(final String selector, final String name) {
        this(selector, name, Type.TEXT);
    }

    public Field(final String selector, final String name, final Type type) {
        this.selector = selector;
        this.name = name;
        this.type = type;
    }

    public static FieldBuilder builder() {
        return new FieldBuilder();
    }

    public String getSelector() {
        return this.selector;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public static class FieldBuilder {
        private String selector;
        private String name;
        private Type type;

        FieldBuilder() {
            type = Type.TEXT;
        }

        public Field.FieldBuilder selector(final String selector) {
            this.selector = selector;
            return this;
        }

        public Field.FieldBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Field.FieldBuilder type(final Type type) {
            this.type = type;
            return this;
        }

        public Field build() {
            return new Field(selector, name, type);
        }

        public String toString() {
            return "Field.FieldBuilder(selector=" + this.selector
                    + ", name=" + this.name
                    + ", type=" + this.type + ")";
        }
    }
}
