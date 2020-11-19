package com.home.primenumber.client;

import com.home.primenumber.client.config.GrpcAppConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(GrpcAppConfig.class)
@SpringBootApplication
public class PrimeNumberProxyServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(PrimeNumberProxyServiceApplication.class, args);
  }
}
