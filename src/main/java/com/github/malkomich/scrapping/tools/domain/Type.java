package com.github.malkomich.scrapping.tools.domain;

import io.webfolder.ui4j.api.dom.Element;

import java.util.Optional;
import java.util.stream.Collectors;

public enum Type {
    TEXT {
        @Override
        public Optional<ScrappedValue> getScrappedValue(final Element container, final String selector) {
            return Optional.ofNullable(container.query(selector))
                    .map(element -> new ScrappedValue<>(element.getText()));
        }
    },
    HREF {
        @Override
        public Optional<ScrappedValue> getScrappedValue(final Element container, final String selector) {
            return Optional.ofNullable(container.query(selector))
                    .map(element -> new ScrappedValue<>(element.getAttribute("href")));
        }
    },
    INTEGER {
        @Override
        public Optional<ScrappedValue> getScrappedValue(final Element container, final String selector) {
            return Optional.ofNullable(container.query(selector))
                    .map(element -> new ScrappedValue<>(Integer.parseInt(element.getText())));
        }
    },
    LIST {
        @Override
        public Optional<ScrappedValue> getScrappedValue(final Element container, final String selector) {
            return Optional.ofNullable(container.queryAll(selector))
                    .map(elements -> elements.stream().map(Element::getText).collect(Collectors.toList()))
                    .map(ScrappedValue::new);
        }
    };

    public abstract Optional<ScrappedValue> getScrappedValue(final Element element, final String selector);
}
