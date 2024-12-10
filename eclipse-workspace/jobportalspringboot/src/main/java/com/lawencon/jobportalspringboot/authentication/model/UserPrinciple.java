package com.lawencon.jobportalspringboot.authentication.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lawencon.jobportalspringboot.persistance.entity.Role;
import com.lawencon.jobportalspringboot.persistance.entity.User;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserPrinciple implements UserDetails {
    
    private User user;
    private Role role;

    private Collection<? extends GrantedAuthority> authorities;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
       return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
