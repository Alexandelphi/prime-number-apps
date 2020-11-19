package com.home.primenumber.client.errorhandling;

public class ApiError {
  private final String title;
  private final String detail;

  public ApiError(String title, String detail) {
    this.title = title;
    this.detail = detail;
  }

  public String getTitle() {
    return title;
  }

  public String getDetail() {
    return detail;
  }

}