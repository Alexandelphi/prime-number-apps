package com.home.primenumber.server;

import com.home.primenumber.proto.PrimeNumberRequest;
import com.home.primenumber.proto.PrimeNumberResponse;
import com.home.primenumber.proto.PrimeNumberServiceGrpc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class PrimeNumberServerTest {
  /**
   * This rule manages automatic graceful shutdown for the registered servers and channels at the
   * end of test.
   */
  @Rule
  public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  public final PrimeNumberServiceGrpc.PrimeNumberServiceBlockingStub blockingStub;

  public PrimeNumberServerTest() throws IOException {
    // Generate a unique in-process server name.
    String serverName = InProcessServerBuilder.generateName();
    // Create a server, add service, start, and register for automatic graceful shutdown.
    grpcCleanup.register(InProcessServerBuilder.forName(serverName)
        .directExecutor()
        .addService(new PrimeNumberServiceImpl())
        .build()
        .start()
    );
    blockingStub = PrimeNumberServiceGrpc.newBlockingStub(
        // Create a client channel and register for automatic graceful shutdown.
        grpcCleanup.register(InProcessChannelBuilder.forName(serverName)
            .directExecutor()
            .build()
        )
    );
  }

  /**
   * To test the server, make calls with a real stub using the in-process channel, and verify
   * behaviors or state changes from the client side.
   */

  @Test
  public void shouldReturnAllPrimeNumsSuccessfully() {
    PrimeNumberResponse response = blockingStub.generate(
        PrimeNumberRequest.newBuilder()
            .setNumber(17)
            .build()
    );
    assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13, 17), response.getPrimeNumList());
  }

  @Test(expected = StatusRuntimeException.class)
  public void shouldFailWhenWeTryUsingNegativeInputNumber() {
    PrimeNumberResponse response = blockingStub.generate(
        PrimeNumberRequest.newBuilder()
            .setNumber(-123)
            .build()
    );
  }

  @Test
  public void shouldReturnEmptyListOfPrimeNumsWithZeroAsInputNumber() {
    PrimeNumberResponse response = blockingStub.generate(
        PrimeNumberRequest.newBuilder()
            .setNumber(0)
            .build()
    );
    assertEquals(Collections.emptyList(), response.getPrimeNumList());
  }

  @Test(expected = StatusRuntimeException.class)
  public void shouldFailWhenWeTryUsingLimitExceededInputNumber() {
    PrimeNumberResponse response = blockingStub.generate(
        PrimeNumberRequest.newBuilder()
            .setNumber(10_000_001)
            .build()
    );
  }
}