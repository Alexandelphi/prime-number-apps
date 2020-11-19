package com.home.primenumber.client;

import com.home.primenumber.proto.PrimeNumberRequest;
import com.home.primenumber.proto.PrimeNumberServiceGrpc.PrimeNumberServiceBlockingStub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimeNumberGrpcClient {

  private static Logger log = LoggerFactory.getLogger(PrimeNumberGrpcClient.class);
  private PrimeNumberServiceBlockingStub primeNumberStub;

  public PrimeNumberGrpcClient(PrimeNumberServiceBlockingStub primeNumberStub) {
    this.primeNumberStub = primeNumberStub;
  }

  public List<Integer> generatePrimeNums(int number) {
    log.info("Generate prime numbers for number={}", number);
    return primeNumberStub.generate(
        PrimeNumberRequest.newBuilder()
            .setNumber(number)
            .build()
    ).getPrimeNumList();
  }
}
