package com.example.event_bus;

import io.vertx.core.AbstractVerticle;

public class HelloVerticle extends AbstractVerticle {

  @Override
  public void start(){
    vertx.eventBus().consumer("hello.vertx.addr", msg->{
      msg.reply("Hello vertx from event bus non-blocking http");
    });
    vertx.eventBus().consumer("hello.vertx.name", msg->{
      String name=(String)msg.body();
      System.out.println(name);
      msg.reply("Hello "+name+" from event bus non-blocking http");
    });
  }
}
