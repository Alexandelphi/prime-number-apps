package com.home.primenumber.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class PrimeNumberServer {

  private static final Logger log = LoggerFactory.getLogger(PrimeNumberServer.class);
  private static final int PORT = 9090;
  private Server server;

  public void start() throws IOException {
    server = ServerBuilder.forPort(PORT)
        .addService(new PrimeNumberServiceImpl())
        .build()
        .start();
  }

  public void awaitTermination() throws InterruptedException {
    if (server == null) {
      return;
    }
    server.awaitTermination();
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    PrimeNumberServer server = new PrimeNumberServer();
    log.info("Start server on port: {}", PORT);
    server.start();
    log.info("Server has been started on port: {}", PORT);
    server.awaitTermination();
  }
}
