package org.demo.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/hello")
public class HelloResource {
@GET
   public String sayHello() {
      log.info("hello");
      return "Hello World";
   }
}
