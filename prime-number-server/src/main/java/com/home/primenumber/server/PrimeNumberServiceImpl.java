package com.home.primenumber.server;

import com.google.rpc.Code;
import com.google.rpc.Status;

import com.home.primenumber.proto.PrimeNumberRequest;
import com.home.primenumber.proto.PrimeNumberResponse;
import com.home.primenumber.proto.PrimeNumberServiceGrpc;
import com.home.primenumber.server.util.PrimeNumberUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;

public class PrimeNumberServiceImpl extends PrimeNumberServiceGrpc.PrimeNumberServiceImplBase {

  private static final Logger log = LoggerFactory.getLogger(PrimeNumberServiceImpl.class);

  private final PrimeNumberUtil primeNumberUtil;

  public PrimeNumberServiceImpl() {
    primeNumberUtil = PrimeNumberUtil.getInstance();
  }

  @Override
  public void generate(PrimeNumberRequest request, StreamObserver<PrimeNumberResponse> responseObserver) {
    log.info("Incoming request in order to generate prime nums, number={}", request.getNumber());
    try {
      PrimeNumberResponse response = PrimeNumberResponse.newBuilder()
          .addAllPrimeNum(primeNumberUtil.generatePrimeNums(request.getNumber()))
          .build();
      responseObserver.onNext(response);
      responseObserver.onCompleted();
      log.info("Prime nums have been generated and sent to the client");
    } catch (Exception ex) {
      Status status = Status.newBuilder()
          .setCode(Code.INVALID_ARGUMENT_VALUE)
          .setMessage(ex.getMessage())
          .build();
      log.error("Something went wrong during generating prime nums", ex);
      responseObserver.onError(StatusProto.toStatusException(status));
    }
  }
}
