package com.home.primenumber.server.util;

import com.home.primenumber.server.exception.PrimeNumberGeneratorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PrimeNumberUtil {

  private static Logger log = LoggerFactory.getLogger(PrimeNumberUtil.class);

  private static final int DEFAULT_AMOUNT = 10_000_000;
  private static volatile PrimeNumberUtil instance;
  private boolean[] nonPrime;

  private PrimeNumberUtil() {
    populateNonPrimes(DEFAULT_AMOUNT + 1);
  }

  // Sieve of Eratosthenes Algorithm, optimized version
  private void populateNonPrimes(int n) {
    log.info("Start populating non prime nums cache");
    nonPrime = new boolean[n];
    nonPrime[1] = true;
    for (int i = 2; i < Math.sqrt(n); i++) {
      if (nonPrime[i]) continue;
      int j = i * 2;
      while (j < n) {
        if (!nonPrime[j]) {
          nonPrime[j] = true;
        }
        j += i;
      }
    }
    log.info("Non prime nums cache has been created for amount={}", DEFAULT_AMOUNT);
  }

  public static PrimeNumberUtil getInstance() {
    PrimeNumberUtil localInstance = instance;
    if (localInstance == null) {
      synchronized (PrimeNumberUtil.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new PrimeNumberUtil();
          log.info("PrimeNumberUtil has been initialised.");
        }
      }
    }
    return localInstance;
  }

  public List<Integer> generatePrimeNums(int number) {
    log.info("Generate prime numbers for number={}", number);
    if (number < 0 || number > DEFAULT_AMOUNT) {
      String msg = String.format(
          "The default limit of prime numbers exceeded. Should be from 0 to %d",
          DEFAULT_AMOUNT
      );
      log.error(msg);
      throw new PrimeNumberGeneratorException(msg);
    }
    List<Integer> primeNums = new ArrayList<>();
    for (int i = 2; i <= number; i++) {
      if (!nonPrime[i]) {
        primeNums.add(i);
      }
    }
    return primeNums;
  }
}
