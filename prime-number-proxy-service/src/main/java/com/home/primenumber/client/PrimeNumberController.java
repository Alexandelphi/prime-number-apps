package com.home.primenumber.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrimeNumberController {

  private PrimeNumberGrpcClient client;

  public PrimeNumberController(PrimeNumberGrpcClient client) {
    this.client = client;
  }

  @GetMapping("/prime/{number}")
  public List<Integer> getPrimeNumbers(@PathVariable int number) {
    return client.generatePrimeNums(number);
  }
}
