package com.github.malkomich.scrapping.tools.service;

import com.github.malkomich.scrapping.tools.domain.Field;
import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;
import com.github.malkomich.scrapping.tools.domain.Type;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.webfolder.ui4j.api.browser.BrowserEngine;
import io.webfolder.ui4j.api.browser.BrowserFactory;
import io.webfolder.ui4j.api.browser.Page;
import io.webfolder.ui4j.api.dom.Document;
import io.webfolder.ui4j.api.dom.Element;
import io.webfolder.ui4j.api.util.Ui4jException;
import mockit.MockUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static com.github.malkomich.scrapping.tools.service.Ui4jScrappingService.SCRAPPING_EMPTY_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(VertxExtension.class)
class Ui4jScrappingServiceTest {

    private static final String URL = "https://www.google.com";
    private static final String CONTAINER_SELECTOR = "body";
    private static final String FIELD_NAME = "fieldName";
    private static final String FIELD_SELECTOR = "fieldSelector";
    private static final String HREF = "href";

    private static final String ELEMENT_TEXT = "elementText";
    private static final String ELEMENT_NUMBER = "1";
    private static final Integer NUMBER = 1;
    private static final String ELEMENT_LINK = "elementLink";
    private static final List<String> ELEMENT_LIST = Collections.singletonList(ELEMENT_TEXT);

    @Mock
    private BrowserEngine browserEngine;
    @Mock
    private Page page;
    @Mock
    private Document document;
    @Mock
    private Element container;
    @Mock
    private Element element;

    private Ui4jScrappingService scrappingService;

    @BeforeEach
    void setUp(final Vertx vertx) {
        initMocks(this);
        mockWebKit();
        scrappingService = new Ui4jScrappingService(vertx);
    }

    @Test
    void browserPageNull() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(null);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertEquals(SCRAPPING_EMPTY_ERROR, handler.cause().getMessage());
        });
    }

    @Test
    void pageUi4jException() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenThrow(Ui4jException.class);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertNotNull(handler.cause());
        });
    }

    @Test
    void pageDocumentNull() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(null);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertEquals(SCRAPPING_EMPTY_ERROR, handler.cause().getMessage());
        });
    }

    @Test
    void documentUi4jException() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenThrow(Ui4jException.class);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertNotNull(handler.cause());
        });
    }

    @Test
    void documentQueryNull() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(null);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertEquals(SCRAPPING_EMPTY_ERROR, handler.cause().getMessage());
        });
    }

    @Test
    void containerUi4jException() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenThrow(Ui4jException.class);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertNotNull(handler.cause());
        });
    }

    @Test
    void containerQueryNull() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(null);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertTrue(handler.result().getContainers().get(0).getFields().isEmpty());
        });
    }

    @Test
    void elementUi4jException() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(element);
        when(element.getText()).thenThrow(Ui4jException.class);

        scrappingService.execute(request, handler -> {
            assertNull(handler.result());
            assertNotNull(handler.cause());
        });
    }

    @Test
    void elementTextNull() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(element);
        when(element.getText()).thenReturn(null);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertNull(handler.result().getContainers().get(0).getFields().get(FIELD_NAME).getValue());
        });
    }

    @Test
    void elementLinkNull() {
        final ScrappingRequest request = requestFixture(Type.HREF);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(element);
        when(element.getAttribute(HREF)).thenReturn(null);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertNull(handler.result().getContainers().get(0).getFields().get(FIELD_NAME).getValue());
        });
    }

    @Test
    void scrapTextElement() {
        final ScrappingRequest request = requestFixture(Type.TEXT);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(element);
        when(element.getText()).thenReturn(ELEMENT_TEXT);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertEquals(ELEMENT_TEXT, handler.result().getContainers().get(0).getFields().get(FIELD_NAME).getValue());
        });
    }

    @Test
    void scrapIntegerElement() {
        final ScrappingRequest request = requestFixture(Type.INTEGER);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(element);
        when(element.getText()).thenReturn(ELEMENT_NUMBER);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertEquals(NUMBER, handler.result().getContainers().get(0).getFields().get(FIELD_NAME).getValue());
        });
    }

    @Test
    void scrapLinkElement() {
        final ScrappingRequest request = requestFixture(Type.HREF);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.query(FIELD_SELECTOR)).thenReturn(element);
        when(element.getAttribute(HREF)).thenReturn(ELEMENT_LINK);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertEquals(ELEMENT_LINK, handler.result().getContainers().get(0).getFields().get(FIELD_NAME).getValue());
        });
    }

    @Test
    void scrapListElement() {
        final ScrappingRequest request = requestFixture(Type.LIST);

        when(browserEngine.navigate(URL)).thenReturn(page);
        when(page.getDocument()).thenReturn(document);
        when(document.queryAll(CONTAINER_SELECTOR)).thenReturn(Collections.singletonList(container));
        when(container.queryAll(FIELD_SELECTOR)).thenReturn(Collections.singletonList(element));
        when(element.getText()).thenReturn(ELEMENT_TEXT);

        scrappingService.execute(request, handler -> {
            assertNotNull(handler.result());
            assertFalse(handler.result().getContainers().isEmpty());
            assertEquals(ELEMENT_LIST, handler.result().getContainers().get(0).getFields().get(FIELD_NAME).getValue());
        });
    }

    @Test
    void close() {
        scrappingService.close();
    }

    private ScrappingRequest requestFixture(final Type type) {
        return ScrappingRequest.builder()
                .url(URL)
                .containerSelector(CONTAINER_SELECTOR)
                .fields(Collections.singletonList(fieldFixture(type)))
                .build();
    }

    private Field fieldFixture(final Type type) {
        return Field.builder()
                .name(FIELD_NAME)
                .selector(FIELD_SELECTOR)
                .type(type)
                .build();
    }

    private void mockWebKit() {
        new MockUp<BrowserFactory>() {
            @mockit.Mock
            public BrowserEngine getWebKit() {
                return browserEngine;
            }
        };
    }
}
