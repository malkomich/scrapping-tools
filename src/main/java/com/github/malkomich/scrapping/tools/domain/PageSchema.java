package com.github.malkomich.scrapping.tools.domain;

import java.util.Collection;

public interface PageSchema {

    String getURL(final String endpoint);

    String containerSelector();

    Collection<Field> fields();
}
