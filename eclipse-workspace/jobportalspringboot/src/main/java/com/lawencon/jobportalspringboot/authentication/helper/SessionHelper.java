package com.lawencon.jobportalspringboot.authentication.helper;



import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lawencon.jobportalspringboot.authentication.model.UserPrinciple;
import com.lawencon.jobportalspringboot.persistance.entity.User;

@Component
public class SessionHelper {
    public static User getLoginUser() {
        return ((UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    public static UserPrinciple getUserPrinciple() {
        return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
