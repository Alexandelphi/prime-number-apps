package com.home.primenumber.client;

import com.home.primenumber.proto.PrimeNumberRequest;
import com.home.primenumber.proto.PrimeNumberResponse;
import com.home.primenumber.proto.PrimeNumberServiceGrpc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import java.io.IOException;
import java.util.Arrays;

import io.grpc.ManagedChannel;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalAnswers.delegatesTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
public class PrimeNumberGrpcClientTest {

  @Rule
  public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  private PrimeNumberGrpcClient client;
  private final PrimeNumberServiceGrpc.PrimeNumberServiceImplBase serviceImpl;

  public PrimeNumberGrpcClientTest() throws Exception {
    serviceImpl = mock(PrimeNumberServiceGrpc.PrimeNumberServiceImplBase.class, delegatesTo(
        new PrimeNumberServiceGrpc.PrimeNumberServiceImplBase() {
          @Override
          public void generate(PrimeNumberRequest request, StreamObserver<PrimeNumberResponse> responseObserver) {
            responseObserver.onNext(
                PrimeNumberResponse.newBuilder()
                    .addAllPrimeNum(Arrays.asList(2, 3, 5, 7, 11, 13, 17))
                    .build()
            );
            responseObserver.onCompleted();
          }
        })
    );
    setUp();
  }

  public void setUp() throws IOException {
    // Generate a unique in-process server name.
    String serverName = InProcessServerBuilder.generateName();
    // Create a server, add service, start, and register for automatic graceful shutdown.
    grpcCleanup.register(InProcessServerBuilder
        .forName(serverName).directExecutor().addService(serviceImpl).build().start());
    // Create a client channel and register for automatic graceful shutdown.
    ManagedChannel managedChannel = grpcCleanup.register(
        InProcessChannelBuilder.forName(serverName).directExecutor().build());
    // Create a PrimeNumberGrpcClient using the in-process channel;
    client = new PrimeNumberGrpcClient(PrimeNumberServiceGrpc.newBlockingStub(managedChannel));
  }

  /**
   * To test the client, call from the client against the fake server, and verify behaviors or state
   * changes from the server side.
   */
  @Test
  public void shouldDeliverMessageToServer() {
    ArgumentCaptor<PrimeNumberRequest> requestCaptor = ArgumentCaptor.forClass(PrimeNumberRequest.class);
    client.generatePrimeNums(17);
    verify(serviceImpl).generate(
        requestCaptor.capture(),
        ArgumentMatchers.any()
    );
    assertEquals(17, requestCaptor.getValue().getNumber());
  }

}