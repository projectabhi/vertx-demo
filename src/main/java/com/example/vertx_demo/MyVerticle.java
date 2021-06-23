package com.example.vertx_demo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;

public class MyVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    /*vertx.createHttpServer().requestHandler(req -> {
      if(req.path().startsWith("/api/hello"))
        req.response().end("Hi from my verticle");
    }).listen(8888);*/
    Router router=Router.router(vertx);

    router.get("/api/hello").handler(ctx->{
      ctx.request().response().end("Hi from my verticle");
    });

    router.get("/api/hello/:name").handler(ctx -> {
      String name = ctx.pathParam("name");
      ctx.request().response().end("Hi "+name);
    });

    vertx.createHttpServer().requestHandler(router).listen(8888);
  }
}
