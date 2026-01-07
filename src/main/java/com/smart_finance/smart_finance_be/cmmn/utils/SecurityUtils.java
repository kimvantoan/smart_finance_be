package com.smart_finance.smart_finance_be.cmmn.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.smart_finance.smart_finance_be.security.service.UserDetailsImpl;

@Component
public class SecurityUtils {

    public static UserDetailsImpl getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        return (UserDetailsImpl) auth.getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public static String getCurrentEmail() {
        return getCurrentUser().getUsername();
    }
}

