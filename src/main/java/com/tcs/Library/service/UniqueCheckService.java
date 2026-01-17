package com.tcs.Library.service;

public interface UniqueCheckService {
    boolean isEmailRegistered(String email);

    boolean isMobileRegistered(String countryCode, String mobileNumber);
}
