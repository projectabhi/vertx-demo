package com.example.event_bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class EventBusMainVerticla extends AbstractVerticle {

  @Override
  public void start(){
    vertx.deployVerticle(new HelloVerticle());
    Router router= Router.router(vertx);
    router.get("/api/hello").handler(this::helloVertx);
    router.get("/api/hello/:name").handler(this::helloName);

    vertx.createHttpServer().requestHandler(router).listen(8080);
  }

  void helloVertx(RoutingContext ctx){
    vertx.eventBus().request("hello.vertx.addr","",reply->{
        ctx.request().response().end((String)reply.result().body());
    });
  }
  void helloName(RoutingContext ctx){
    String name=ctx.pathParam("name");
    vertx.eventBus().request("hello.vertx.name",name, reply->{
      ctx.request().response().end((String)reply.result().body());
    });
  }
}
