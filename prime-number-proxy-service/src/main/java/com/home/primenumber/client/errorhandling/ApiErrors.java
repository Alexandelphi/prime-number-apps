package com.home.primenumber.client.errorhandling;

import java.util.Collections;
import java.util.List;

public class ApiErrors {

  private final List<ApiError> errors;

  public ApiErrors(List<ApiError> errors) {
    this.errors = errors;
  }

  public static ApiErrors create(String title, String message) {
    return new ApiErrors(Collections.singletonList(new ApiError(title, message)));
  }

  public static ApiErrors create(String message) {
    return create("", message);
  }

  public List<ApiError> getErrors() {
    return errors;
  }
}