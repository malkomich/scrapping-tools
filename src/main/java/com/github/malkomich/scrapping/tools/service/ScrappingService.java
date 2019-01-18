package com.github.malkomich.scrapping.tools.service;

import com.github.malkomich.scrapping.tools.domain.ScrappingRequest;
import com.github.malkomich.scrapping.tools.domain.ScrappingResponse;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

public interface ScrappingService {

    void execute(final ScrappingRequest request, final Handler<AsyncResult<ScrappingResponse>> handler);

    void close();
}
