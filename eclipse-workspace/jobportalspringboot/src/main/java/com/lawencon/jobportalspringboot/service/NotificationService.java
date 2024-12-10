package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.persistance.entity.Notification;

public interface NotificationService {
    List<Notification> getAllNotification();
    List<Notification> getNotificationsByUser(String userId);
}
