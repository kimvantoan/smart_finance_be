package com.smart_finance.smart_finance_be.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

}
