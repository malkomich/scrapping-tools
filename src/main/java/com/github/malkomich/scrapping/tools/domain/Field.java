package com.github.malkomich.scrapping.tools.domain;

import io.vertx.core.json.JsonObject;

public class Field {
    private String name;
    private String selector;
    private Type type = Type.TEXT;

    Field(final JsonObject json) {
        this.name = json.getString("name");
        this.selector = json.getString("selector");
        this.type = Type.valueOf(json.getString("type"));
    }

    public Field(final String name, final String selector, final Type type) {
        this.name = name;
        this.selector = selector;
        this.type = type;
    }

    public static FieldBuilder builder() {
        return new FieldBuilder();
    }

    public String getName() {
        return this.name;
    }

    public String getSelector() {
        return this.selector;
    }

    public Type getType() {
        return this.type;
    }

    public static class FieldBuilder {
        private String name;
        private String selector;
        private Type type;

        FieldBuilder() {
        }

        public Field.FieldBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public Field.FieldBuilder selector(final String selector) {
            this.selector = selector;
            return this;
        }

        public Field.FieldBuilder type(final Type type) {
            this.type = type;
            return this;
        }

        public Field build() {
            return new Field(name, selector, type);
        }

        public String toString() {
            return "Field.FieldBuilder(name=" + this.name
                    + ", selector=" + this.selector
                    + ", type=" + this.type + ")";
        }
    }
}
