package com.example.cluster_Infinispan;

import com.example.event_bus.HelloVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class InfinispanClusterVerticle extends AbstractVerticle {

  String id= UUID.randomUUID().toString();

  @Override
  public void start(){
    vertx.deployVerticle(new HelloVerticle());

    Router router= Router.router(vertx);
    router.get("/api/hello").handler(this::helloVertx);
    router.get("/api/hello/:name").handler(this::helloName);

    int port;
    try{
      port = Integer.parseInt(System.getProperty("http.port","8080"));
    }catch (NumberFormatException e){
      port=8080;
    }
    vertx.createHttpServer().requestHandler(router).listen(port);
  }

  void helloVertx(RoutingContext ctx){
    vertx.eventBus().request("hello.vertx.addr","",reply->{
      ctx.request().response().end((String)reply.result().body()+" from:"+id);
    });
  }
  void helloName(RoutingContext ctx){
    String name=ctx.pathParam("name");
    vertx.eventBus().request("hello.vertx.name",name, reply->{
      ctx.request().response().end((String)reply.result().body());
    });
  }
}
