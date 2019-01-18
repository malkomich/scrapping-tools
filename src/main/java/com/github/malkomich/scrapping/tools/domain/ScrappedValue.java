package com.github.malkomich.scrapping.tools.domain;

public class ScrappedValue<T> {

    private T value;

    public ScrappedValue(final T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }
}
