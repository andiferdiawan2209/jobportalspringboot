package com.lawencon.jobportalspringboot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.jobportalspringboot.persistance.entity.Notification;
import com.lawencon.jobportalspringboot.persistance.entity.Status;
import com.lawencon.jobportalspringboot.persistance.repository.NotificationRepository;
import com.lawencon.jobportalspringboot.service.NotificationService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    @Override
    public List<Notification>  getAllNotification() {
        return notificationRepository.findAll();
    }
    @Override
    public List<Notification> getNotificationsByUser(String userId) {
         return notificationRepository.findByUser_Id(userId);
    }
    
}
