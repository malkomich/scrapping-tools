package com.github.malkomich.scrapping.tools.util;

import com.github.malkomich.scrapping.tools.domain.PageSchema;
import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;

import java.lang.reflect.InvocationTargetException;

public class ScrappingUtils {

    public static ScrappingRequest generateRequest(final Class<? extends PageSchema> clazz,
                                                   final String endpoint) {
        final PageSchema pageSchema = buildSchema(clazz);
        return ScrappingRequest.builder()
                .url(pageSchema.getURL(endpoint))
                .containerSelector(pageSchema.containerSelector())
                .fields(pageSchema.fields())
                .build();
    }

    private static PageSchema buildSchema(final Class<? extends PageSchema> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Constructor not accessible");
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Error instantiating schema");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Constructor not found");
        }
        return null;
    }
}
