package com.lawencon.jobportalspringboot.service;

import java.util.List;

import com.lawencon.jobportalspringboot.model.response.CandidateNotifResponse;

public interface CandidateService {
    List<CandidateNotifResponse> findAllNotif();
}
