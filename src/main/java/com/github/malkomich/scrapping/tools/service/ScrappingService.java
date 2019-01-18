package com.github.malkomich.scrapping.tools.service;

import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;
import io.vertx.core.Future;

public interface ScrappingService {

    void execute(final ScrappingRequest request, final Future<Object> future);
}
