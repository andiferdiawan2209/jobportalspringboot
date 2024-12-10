package com.lawencon.jobportalspringboot.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.model.response.CandidateNotifResponse;
import com.lawencon.jobportalspringboot.model.response.EmployementTypeResponse;
import com.lawencon.jobportalspringboot.persistance.entity.EmployementType;
import com.lawencon.jobportalspringboot.persistance.entity.Notification;
import com.lawencon.jobportalspringboot.persistance.entity.User;
import com.lawencon.jobportalspringboot.service.CandidateService;
import com.lawencon.jobportalspringboot.service.NotificationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final NotificationService notificationService;
    @Override
    public List<CandidateNotifResponse> findAllNotif() {
        User user = SessionHelper.getLoginUser();
        List<Notification> notifications = notificationService.getNotificationsByUser(user.getId());

        List<CandidateNotifResponse> result = new ArrayList<>();
        for (Notification item : notifications) {
            CandidateNotifResponse response = masToResponse(item);
            result.add(response);
        }
        return result;
    }
    
     private CandidateNotifResponse masToResponse(Notification item) {
        CandidateNotifResponse response = new CandidateNotifResponse();
        response.setJobTitle(item.getTrxJobVacancy().getJobvVacancy().getJobTitle().getTitle());
        response.setLocation(item.getTrxJobVacancy().getJobvVacancy().getLocation().getName());
        response.setMessage(item.getMessage());
        response.setSalaryMax(item.getTrxJobVacancy().getJobvVacancy().getSalaryMax().toString());
        response.setSalaryMin(item.getTrxJobVacancy().getJobvVacancy().getSalaryMin().toString());
        return response;
    }
    
}
