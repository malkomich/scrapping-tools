# Scrapping Tools

[![Build Status](https://travis-ci.org/malkomich/scrapping-tools.svg?branch=master)](https://travis-ci.org/malkomich/scrapping-tools)


## Overview

Component for scrapping webs in a generic way.


#### Requisites
* Java 8
* Maven 3


## Install

**Gradle**

```
    dependencies {
        compile 'com.github.malkomich:scrapping-tools:1.0.0'
    }
```

**Maven**

```
    <dependency>
        <groupId>com.github.malkomich</groupId>
        <artifactId>scrapping-tools</artifactId>
        <version>1.0.0</version>
    </dependency>
```


## Usage

##### INITIALIZATION:
```java
final ScrappingService scrappingService = new Ui4jScrappingService(vertx);
```

##### SCRAPPING SCHEMA:
```java
public class CustomPageSchema implements PageSchema {
    private static final String BASE_URL = "https://www.the-url-domain-to-scrap.com/";
    private static final String CONTAINER_SELECTOR = ".container";

    private static final String SELECTOR = "h1 .a-beautiful-title";
    private static final String NAME = "name";
    

    @Override
    public String getURL(final String endpoint) {
        return BASE_URL.concat(endpoint);
    }

    @Override
    public String containerSelector() {
        return CONTAINER_SELECTOR;
    }

    @Override
    public Collection<Field> fields() {
        return Lists.newArrayList(
                new Field(SELECTOR, NAME, Type.INTEGER));
    }
}
```

##### SCRAPPING EXECUTION:
```java
final String endpoint = "endpoint-to-scrap";
final ScrappingRequest request = ScrappingUtils.generateRequest(CustomPageSchema.class, endpoint);

scrappingService.execute(request, onScrapped -> {
    if (onScrapped.succeeded()) {
        future.complete();
    } else {
        future.fail(onScrapped.cause());
    }
});
```


## License

[Apache License](http://www.apache.org/licenses/LICENSE-2.0.txt)
