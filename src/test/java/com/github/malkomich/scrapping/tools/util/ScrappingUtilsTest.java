package com.github.malkomich.scrapping.tools.util;

import com.github.malkomich.scrapping.tools.domain.Field;
import com.github.malkomich.scrapping.tools.domain.PageSchema;
import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;
import com.github.malkomich.scrapping.tools.domain.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScrappingUtilsTest {

    private static final String ENDPOINT = "endpoint";
    private static final String URL = "url";
    private static final String CONTAINER_SELECTOR = "containerSelector";
    private static final String FIELD_NAME = "fieldName";
    private static final String FIELD_SELECTOR = "fieldSelector";
    private static final Type FIELD_TYPE = Type.HREF;

    @Test
    void generateRequestWithNullSchema() {
        final Executable requestGenerationError = () -> ScrappingUtils.generateRequest(null, ENDPOINT);
        assertThrows(NullPointerException.class, requestGenerationError);
    }

    @Test
    void generateRequestWithNoImplementedSchema() {
        final Executable requestGenerationError = () -> ScrappingUtils.generateRequest(PageSchema.class, null);
        assertThrows(RuntimeException.class, requestGenerationError);
    }

    @Test
    void generateRequestWithNullEndpoint() {
        final ScrappingRequest request = ScrappingUtils.generateRequest(SchemaFixture.class, null);
        assertNotNull(request);
    }

    @Test
    void generateRequestWithInvalidConstructor() {
        final Executable requestGenerationError = () -> ScrappingUtils
                .generateRequest(SchemaFixtureInvalidConstructor.class, ENDPOINT);
        assertThrows(RuntimeException.class, requestGenerationError);
    }

    @Test
    void generateRequestWithPrivateConstructor() {
        final Executable requestGenerationError = () -> ScrappingUtils
                .generateRequest(SchemaFixturePrivateConstructor.class, ENDPOINT);
        assertThrows(RuntimeException.class, requestGenerationError);
    }

    @Test
    void generateRequestSuccessfully() {
        final ScrappingRequest request = ScrappingUtils
                .generateRequest(SchemaFixture.class, ENDPOINT);
        assertNotNull(request);
        assertEquals(URL, request.getUrl());
        assertEquals(CONTAINER_SELECTOR, request.getContainerSelector());
        assertEquals(1, request.getFields().size());
        assertEquals(FIELD_NAME, request.getFields().iterator().next().getName());
        assertEquals(FIELD_SELECTOR, request.getFields().iterator().next().getSelector());
        assertEquals(FIELD_TYPE, request.getFields().iterator().next().getType());
    }

    public static class SchemaFixture implements PageSchema {

        @Override
        public String getURL(final String endpoint) {
            return URL;
        }

        @Override
        public String containerSelector() {
            return CONTAINER_SELECTOR;
        }

        @Override
        public Collection<Field> fields() {
            final Field field = Field.builder()
                    .name(FIELD_NAME)
                    .selector(FIELD_SELECTOR)
                    .type(FIELD_TYPE)
                    .build();
            return Collections.singletonList(field);
        }
    }

    public static class SchemaFixturePrivateConstructor implements PageSchema {

        private SchemaFixturePrivateConstructor() {

        }

        @Override
        public String getURL(final String endpoint) {
            return null;
        }

        @Override
        public String containerSelector() {
            return null;
        }

        @Override
        public Collection<Field> fields() {
            return null;
        }
    }

    public static class SchemaFixtureInvalidConstructor implements PageSchema {

        private byte field;

        public SchemaFixtureInvalidConstructor(final byte parameter) {
            field = parameter;
        }

        @Override
        public String getURL(final String endpoint) {
            return null;
        }

        @Override
        public String containerSelector() {
            return null;
        }

        @Override
        public Collection<Field> fields() {
            return null;
        }
    }
}


