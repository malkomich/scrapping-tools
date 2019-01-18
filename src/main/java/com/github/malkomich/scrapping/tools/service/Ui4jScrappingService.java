package com.github.malkomich.scrapping.tools.service;

import com.github.malkomich.scrapping.tools.domain.Field;
import com.github.malkomich.scrapping.tools.domain.ScrappedValue;
import com.github.malkomich.scrapping.tools.domain.ScrappingContainer;
import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;
import com.github.malkomich.scrapping.tools.domain.ScrappingResponse;
import io.vertx.core.Future;
import io.webfolder.ui4j.api.browser.Page;
import io.webfolder.ui4j.api.dom.Element;

import javax.xml.ws.WebServiceException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.webfolder.ui4j.api.browser.BrowserFactory.getWebKit;

public class Ui4jScrappingService implements ScrappingService {

    @Override
    public void execute(final ScrappingRequest request, final Future<Object> future) {
        try (final Page page = getWebKit().navigate(request.getUrl())) {
            final ScrappingResponse response = ScrappingResponse.builder()
                    .containers(executeScrap(page, request))
                    .build();
            future.complete(response);
        } catch (final WebServiceException error) {
            future.fail(error);
        }
    }

    private List<ScrappingContainer> executeScrap(final Page page,
                                                  final ScrappingRequest request) {
        return page.getDocument()
                .queryAll(request.getContainerSelector())
                .stream()
                .map(element -> scrapContainer(element, request))
                .collect(Collectors.toList());
    }

    private ScrappingContainer scrapContainer(final Element container, final ScrappingRequest request) {
        final Map<String, ScrappedValue> fields = new HashMap<>();
        request.getFields()
                .forEach(field -> Objects.requireNonNull(getValue(field, container))
                        .ifPresent(value -> fields.put(field.getName(), value)));
        return ScrappingContainer.builder()
                .fields(fields)
                .build();
    }

    private Optional<ScrappedValue> getValue(final Field field, final Element container) {
        return Optional.ofNullable(field)
                .map(Field::getType)
                .flatMap(type -> type.getScrappedValue(container, field.getSelector()));
    }
}
