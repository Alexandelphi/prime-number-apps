package com.home.primenumber.client.config;

import com.home.primenumber.proto.PrimeNumberServiceGrpc;
import com.home.primenumber.proto.PrimeNumberServiceGrpc.PrimeNumberServiceBlockingStub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
@ComponentScan("com.home.primenumber.client")
public class GrpcAppConfig {

  @Value("${grpc.server.host}")
  private String host;

  @Value("${grpc.server.port}")
  private int port;

  private ManagedChannel managedChannel;

  @Bean(name = "primeNumberServiceBlockingStub")
  public PrimeNumberServiceBlockingStub primeNumberServiceBlockingStub() {
    managedChannel = ManagedChannelBuilder.forAddress(host, port)
        .usePlaintext()
        .build();
    return PrimeNumberServiceGrpc.newBlockingStub(managedChannel);
  }

  @PreDestroy
  public void preDestroy() {
    managedChannel.shutdown();
  }
}
