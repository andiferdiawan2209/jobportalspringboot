package com.lawencon.jobportalspringboot.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.model.UserPrinciple;


@Component
public class AuditorAwareImpl implements AuditorAware<String> { 

    @Override
    public Optional<String> getCurrentAuditor() {
        // Periksa apakah ada otentikasi aktif
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
        try {
            // Jika tidak ada otentikasi, gunakan alamat host lokal
            return Optional.of(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown Host");
        }
    } else {
        // Jika ada otentikasi, coba dapatkan `UserPrinciple`
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrinciple) {
            return Optional.of(((UserPrinciple) principal).getUsername());
        } else {
            // Jika tipe principal tidak sesuai, kembalikan default "SYSTEM"
            return Optional.of("SYSTEM");
        }
    }

        
    }

}
