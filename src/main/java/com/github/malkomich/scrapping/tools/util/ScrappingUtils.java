package com.github.malkomich.scrapping.tools.util;

import com.github.malkomich.scrapping.tools.domain.PageSchema;
import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;

import java.util.Objects;

public class ScrappingUtils {

    public static ScrappingRequest generateRequest(final Class<? extends PageSchema> clazz,
                                                   final String endpoint) {
        final PageSchema pageSchema = buildSchema(Objects.requireNonNull(clazz));
        return ScrappingRequest.builder()
                .url(pageSchema.getURL(endpoint))
                .containerSelector(pageSchema.containerSelector())
                .fields(pageSchema.fields())
                .build();
    }

    private static PageSchema buildSchema(final Class<? extends PageSchema> clazz) {
        try {
            return clazz.newInstance();
        } catch (final InstantiationException e) {
            throw new RuntimeException("Schema could not be instantiate");
        } catch (final IllegalAccessException e) {
            throw new RuntimeException("Constructor not accessible");
        }
    }
}
