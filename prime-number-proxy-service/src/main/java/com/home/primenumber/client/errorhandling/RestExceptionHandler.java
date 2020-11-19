package com.home.primenumber.client.errorhandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;


@ControllerAdvice
public class RestExceptionHandler {

  private static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(value = {Exception.class})
  public ApiErrors unhandledException(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ApiErrors.create("Server Error", "");
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(value = {StatusRuntimeException.class})
  public ApiErrors grpcException(StatusRuntimeException ex) {
    log.error(ex.getMessage(), ex);
    Status status = ex.getStatus();
    if (status.getCode().equals(Status.Code.UNAVAILABLE)) {
      return ApiErrors.create(status.getCode().name(), "The gRPC server is unavailable.");
    }
    return ApiErrors.create(status.getCode().name(), status.getDescription());
  }

}
