package com.tcs.Library.service;

import org.springframework.stereotype.Service;
import com.tcs.Library.repository.UserRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UniqueCheckServiceImpl implements UniqueCheckService {

    private final UserRepo userRepo;

    public boolean isEmailRegistered(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean isMobileRegistered(String countryCode, String mobileNumber) {
        // This method is no longer needed as mobile number is not a unique constraint
        // Keeping for backwards compatibility, always returns false
        return false;
    }
}
