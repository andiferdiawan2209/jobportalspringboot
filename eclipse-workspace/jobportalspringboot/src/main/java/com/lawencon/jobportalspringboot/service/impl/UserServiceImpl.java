package com.lawencon.jobportalspringboot.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.lawencon.jobportalspringboot.authentication.helper.SessionHelper;
import com.lawencon.jobportalspringboot.authentication.model.UserPrinciple;
import com.lawencon.jobportalspringboot.helper.SpecificationHelper;
import com.lawencon.jobportalspringboot.model.request.CreateCandidateRequest;
import com.lawencon.jobportalspringboot.model.request.CreateUserRequest;
import com.lawencon.jobportalspringboot.model.request.PagingRequest;
import com.lawencon.jobportalspringboot.model.request.UpdateUserRequest;
import com.lawencon.jobportalspringboot.model.response.RoleResponse;
import com.lawencon.jobportalspringboot.model.response.StatusResponse;
import com.lawencon.jobportalspringboot.model.response.UserResponse;
import com.lawencon.jobportalspringboot.persistance.entity.Gender;
import com.lawencon.jobportalspringboot.persistance.entity.OtpUser;
import com.lawencon.jobportalspringboot.persistance.entity.Profile;
import com.lawencon.jobportalspringboot.persistance.entity.Role;
import com.lawencon.jobportalspringboot.persistance.entity.Status;
import com.lawencon.jobportalspringboot.persistance.entity.User;
import com.lawencon.jobportalspringboot.persistance.repository.RoleRepository;
import com.lawencon.jobportalspringboot.persistance.repository.UserRepository;
import com.lawencon.jobportalspringboot.service.GenderService;
import com.lawencon.jobportalspringboot.service.OtpService;
import com.lawencon.jobportalspringboot.service.ProfileService;
import com.lawencon.jobportalspringboot.service.UserService;
import com.lawencon.jobportalspringboot.spesification.UserSpecification;
import com.sun.security.auth.UserPrincipal;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final GenderService genderService;
    private final ProfileService profileService;
    private final OtpService otpService;
    private final JavaMailSender mailSender;

    @Override
    public List<UserResponse> findAllByUsernameAndEmail(String username, String email) {
        Specification spec = Specification.where(UserSpecification.hasName(username))
                .and(UserSpecification.hasEmail(email));

        List<User> users = userRepository.findAll(spec);

        List<UserResponse> list = new ArrayList<>();
        for (User user : users) {
            UserResponse response = new UserResponse();
            BeanUtils.copyProperties(user, response);
            list.add(response);
        }
        return list;
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username '" + user.getUsername() + "' already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email '" + user.getEmail() + "' already exists");
        }

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User findEntityById(String id) {
        User item = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
            return item;
    }

    @Override
        public Page<UserResponse> findAll(PagingRequest pagingRequest, String inquiry) {
            PageRequest pageRequest = PageRequest.of(pagingRequest.getPage(), pagingRequest.getPageSize(),
                    SpecificationHelper.createSort(pagingRequest.getSortBy()));


            Specification<User> spec = Specification.where(null);
            if (inquiry != null) {
                spec = spec.and(SpecificationHelper.inquiryFilter(Arrays.asList("username", "email"), inquiry));
            }

            Page<User> userResponse = userRepository.findAll(spec, pageRequest);

            List<UserResponse> responses = userResponse.getContent().stream().map( user -> {
                UserResponse usrResponse = new UserResponse();
                BeanUtils.copyProperties(user, usrResponse);
                usrResponse.setRoleCode(user.getRole() != null ? user.getRole().getCode() : "No Role");
                return usrResponse;
            }).toList();

            return new PageImpl<>(responses, pageRequest, userResponse.getTotalElements());
        }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsernameAndPassword(String username, String password) {
        
        Optional<User> user = userRepository.findByUsername(username);
        
        if (user.isPresent()) {
            if (!passwordEncoder.matches(password, user.get().getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password do not match");
            } else {
                return user; 
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }   
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "user is not exist"));
                
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().getCode()));
                return UserPrinciple.builder().user(user).role(user.getRole()).authorities(authorities).build();
            }
            
        };
    }

    @Override
    public void add(CreateUserRequest request) {

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found for ID: " + request.getRoleId()));

        if (!role.getCode().equalsIgnoreCase("HR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID User not HR");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()) );  
        user.setEmail(request.getEmail());
        user.setRole(role);
        user.setIsActive(true);

        userRepository.save(user);
    }

    @Override
    public void edit(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found for ID: " + id));
        Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() ->  new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found for ID: " + request.getRoleId()));
           
          if (!role.getCode().equalsIgnoreCase("HR")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID User not HR");
        }      
        if (request.getUsername() != null) user.setUsername(request.getUsername());
        if (request.getPassword() != null) user.setPassword(passwordEncoder.encode(request.getPassword()) ); 
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getRoleId() != null) {
             user.setRole(role);
        }

        userRepository.save(user);
    }

    @Override
    public List<UserResponse> findAll() {
         List<User> items = userRepository.findAll();
        List<UserResponse> result = new ArrayList<>();
        for (User item : items) {
            UserResponse Response = masToResponse(item);
            result.add(Response);
        }
        return result;
    }

    @Override
    public void delete(String id) {
        String adminCode = SessionHelper.getLoginUser().getRole().getCode();
        if (!adminCode.equals("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only admin can delete");
        }
          Optional<User> Optional = userRepository.findById(id);
        if (Optional.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found");
        }
    }

    @Override
    public UserResponse findByEntityid(String id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found"));
        return masToResponse(user);
    }

    private UserResponse masToResponse(User user) {
        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }

    @Override
    @Transactional
    public void createCandidate(CreateCandidateRequest request) {
        Role role = roleRepository.findByCode("CANDIDATE")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

        Gender gender = genderService.findEntityById(request.getGenderId());
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(role);
        user.setCreatedBy("System");
        user.setUpdatedBy("System");
        user.setUpdatedAt(zonedDateTime);
        user.setCreatedAt(zonedDateTime);
        user.setVersion(0l);
        user.setIsActive(false);

        user = userRepository.saveAndFlush(user);

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFullName(request.getFullName());
        profile.setGender(gender);
        profile.setCreatedBy("System");
        profile.setUpdatedBy("System");
        profile.setUpdatedAt(zonedDateTime);
        profile.setCreatedAt(zonedDateTime);
        profile.setVersion(0l);

        profileService.save(profile);

        String otpCode = RandomStringUtils.randomNumeric(6);
        OtpUser otpUser = new OtpUser();
        otpUser.setUser(user);
        otpUser.setCode(otpCode);
        otpUser.setExpire(zonedDateTime.plusMinutes(15));

        otpService.save(otpUser);

        try {
            sendOtpEmail(user.getEmail(), otpCode);
        } catch (IOException | MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send code OTP");
        }

    }


 
    

    private void sendOtpEmail(String recipientEmail, String otpCode) throws IOException, MessagingException {
        String emailContent = generateOtpEmailContent(otpCode);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("andiferdiawan22@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Kode OTP untuk Aktivasi Akun Anda");
        helper.setText(emailContent, true);
        try{
            mailSender.send(message);
        } catch ( Exception e ) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to Create User");
        }
    }

    @Override
    public void verify(String otpCode) {
        OtpUser otpUser = otpService.findFirstByCode(otpCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP code"));

        if (otpUser.getExpire().isBefore(ZonedDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP code has expired");
        }

        User user = otpUser.getUser();
        user.setIsActive(true);
        userRepository.saveAndFlush(user);

        otpService.delete(otpUser);

        try {
            sendAccountActivatedEmail(user.getEmail());
        } catch (IOException | MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send activation confirmation email");
        }
    }


    @Override
    public void resendOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        otpService.deleteByUser(user);

        String newOtpCode = RandomStringUtils.randomNumeric(6);

        ZonedDateTime expireTime = ZonedDateTime.now().plusMinutes(15);

        OtpUser otpUser = new OtpUser();
        otpUser.setUser(user);
        otpUser.setCode(newOtpCode);
        otpUser.setExpire(expireTime);
        otpService.save(otpUser);

        try {
            sendOtpEmail(user.getEmail(), newOtpCode);
        } catch (IOException | MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to send OTP email");
        }
    }                               

    
    public String generateOtpEmailContent(String otpCode) throws IOException {
        String templatePath = "src/main/resources/templates/email-verif.html";
        String template = loadEmailTemplate(templatePath);
        return template.replace("{{otpCode}}", otpCode);
    }

    public String loadEmailTemplate(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
    
    private void sendAccountActivatedEmail(String recipientEmail) throws IOException, MessagingException {
        String emailContent = loadEmailTemplate("src/main/resources/templates/verif-success.html");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("andiferdiawan22@gmail.com");
        helper.setTo(recipientEmail);
        helper.setSubject("Akun Anda Telah Aktif");
        helper.setText(emailContent, true);

        mailSender.send(message);
    }

    
    
    
}
