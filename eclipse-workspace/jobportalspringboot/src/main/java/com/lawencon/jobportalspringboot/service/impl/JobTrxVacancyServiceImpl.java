package com.lawencon.jobportalspringboot.service.impl;


import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.config.RabbitMQConfig;
import com.lawencon.jobportalspringboot.model.request.CreateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateJobTrxVacancyRequest;
import com.lawencon.jobportalspringboot.model.response.JobVacancyTrxResponse;
import com.lawencon.jobportalspringboot.model.response.NotificationMessage;
import com.lawencon.jobportalspringboot.persistance.entity.JobVacancy;
import com.lawencon.jobportalspringboot.persistance.entity.Status;
import com.lawencon.jobportalspringboot.persistance.entity.TrxJobVacancy;
import com.lawencon.jobportalspringboot.persistance.entity.User;
import com.lawencon.jobportalspringboot.persistance.repository.JobTrxVacancyRepository;
import com.lawencon.jobportalspringboot.persistance.repository.JobVacancyRepository;
import com.lawencon.jobportalspringboot.persistance.repository.StatusRepository;
import com.lawencon.jobportalspringboot.persistance.repository.UserRepository;
import com.lawencon.jobportalspringboot.service.JobTrxVacancyService;
import com.lawencon.jobportalspringboot.model.response.File;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@AllArgsConstructor
public class JobTrxVacancyServiceImpl implements JobTrxVacancyService {
    
    
    private final JobTrxVacancyRepository jobTrxVacancyRepository;
    private final JobVacancyRepository jobVacancyRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final RabbitTemplate rabbitTemplate;


    @Override
    @Transactional
    public void add(CreateJobTrxVacancyRequest request) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can create");
        }
        JobVacancy jobVacancy = jobVacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Job vacancy not found"));

        User recruiter = userRepository.findById(request.getRecruiterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recruiter not found"));

        Status status = statusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status not found"));

        TrxJobVacancy trxJobVacancy = new TrxJobVacancy();
        trxJobVacancy.setJobvVacancy(jobVacancy);
        trxJobVacancy.setRecruiter(recruiter);
        trxJobVacancy.setStatus(status);
        trxJobVacancy.setPublishDate(LocalDate.parse(request.getPublishDate()));

        jobTrxVacancyRepository.saveAndFlush(trxJobVacancy);


    }

    @Override
    public List<JobVacancyTrxResponse> findAll() {
        return jobTrxVacancyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public JobVacancyTrxResponse findById(String id) {
        TrxJobVacancy trxJobVacancy = jobTrxVacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Job Vacancy Transaction not found"));
        return mapToResponse(trxJobVacancy);
    }

    @Override
    @Transactional
    public void edit(String id, UpdateJobTrxVacancyRequest request) {
        TrxJobVacancy trxJobVacancy = jobTrxVacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Job Vacancy Transaction not found"));

        JobVacancy jobVacancy = jobVacancyRepository.findById(request.getVacancyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Job vacancy not found"));

        User recruiter = userRepository.findById(request.getRecruiterId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recruiter not found"));
        
        if (!recruiter.getRole().getCode().equalsIgnoreCase("HR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID User not HR");
        }

        Status status = statusRepository.findById(request.getStatusId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status not found"));

        trxJobVacancy.setJobvVacancy(jobVacancy);
        trxJobVacancy.setRecruiter(recruiter);
        trxJobVacancy.setStatus(status);
        trxJobVacancy.setPublishDate(LocalDate.parse(request.getPublishDate()));

        jobTrxVacancyRepository.save(trxJobVacancy);

        List<User> candidates = userRepository.findAllByRoleCode("CANDIDATE");
        
        for (User candidate : candidates) {
            NotificationMessage notificationMessage = new NotificationMessage();
            notificationMessage.setUserId(candidate.getId());
            notificationMessage.setMessage("New job vacancy available: " + jobVacancy.getJobTitle().getTitle());
            notificationMessage.setVacancyTrxId(trxJobVacancy.getId());
            try {
                rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_TOPIC, "notify.topic", notificationMessage);
            } catch (Exception e) {
                e.printStackTrace(); 
            }

        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        TrxJobVacancy trxJobVacancy = jobTrxVacancyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Job Vacancy Transaction not found"));
        jobTrxVacancyRepository.delete(trxJobVacancy);
    }

    private JobVacancyTrxResponse mapToResponse(TrxJobVacancy trxJobVacancy) {
        return new JobVacancyTrxResponse(
                trxJobVacancy.getId(),
                trxJobVacancy.getJobvVacancy().getJobTitle().getTitle(),
                trxJobVacancy.getRecruiter().getUsername(),
                trxJobVacancy.getPublishDate(),
                trxJobVacancy.getStatus().getName());
    }

    @Override
    public List<JobVacancyTrxResponse> findByRecruiterId() {
        String recruiterId = SessionHelper.getLoginUser().getId();
        List<TrxJobVacancy> jobTrxVacancies = jobTrxVacancyRepository.findByRecruiterId(recruiterId);

        return jobTrxVacancies.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatusById(String vacancyTrxId, String statusId) {
        TrxJobVacancy trxJobVacancy = jobTrxVacancyRepository.findById(vacancyTrxId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job Vacancy Transaction not found"));
        User user = SessionHelper.getLoginUser();

        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status not found"));

        if (user.getRole().getCode().equalsIgnoreCase("ADMIN")) {
            if (!status.getCode().equalsIgnoreCase("PENDING") && !status.getCode().equalsIgnoreCase("ENDED")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your role Can't change status");
            }
        } else if (user.getRole().getCode().equalsIgnoreCase("HR")) {
            if (!status.getCode().equalsIgnoreCase("ONGOING") && !status.getCode().equalsIgnoreCase("ENDED")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your role Can't change status");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your role Can't change status");
        }

        trxJobVacancy.setStatus(status);
        jobTrxVacancyRepository.saveAndFlush(trxJobVacancy);
    }
    
    @Override
    public File getReport() throws JRException {
        File file = new File();
        file.setFileName("Report Ticket");
        file.setFileExt(".pdf");

        List<JobVacancyTrxResponse> reportData = new ArrayList<>();

         reportData.addAll(jobTrxVacancyRepository.findAll().stream()
            .map(this::mapToResponse)
            .toList());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportData);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("dataTable", dataSource.cloneDataSource());

        InputStream filePath = getClass().getClassLoader().getResourceAsStream(
            "templates/vacancy_report.jrxml"
        );

        JasperReport report = JasperCompileManager.compileReport(filePath);

        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
        byte[] bytes = JasperExportManager.exportReportToPdf(print);
        file.setData(bytes);
        return file;
    }
    
    
    
}
