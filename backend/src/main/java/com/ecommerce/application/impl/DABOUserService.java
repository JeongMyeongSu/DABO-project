package com.ecommerce.application.impl;

import com.ecommerce.api.Mailing.MailingService;
import com.ecommerce.domain.dto.daboUserDto;
import com.ecommerce.domain.exception.ApplicationException;
import com.ecommerce.domain.repository.entity.DABOUser;
import com.ecommerce.infrastructure.repository.DABOUserRepository;
//import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DABOUserService {

    private final DABOUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CommonService commonService;
    private final MailingService mailingService;

    @Autowired
    public DABOUserService(DABOUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, CommonService commonService, MailingService mailingService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.commonService = commonService;
        this.mailingService = mailingService;
    }


    public List<DABOUser> list() {
        List<DABOUser> list = userRepository.findAll().stream()
                .sorted((a, b) -> (int) (b.getPoint()-a.getPoint()))
                .collect(Collectors.toList());

        if(!list.isEmpty()){
            return list;
        }
        throw new NullPointerException();
    }

    // 닉네임 중복 검사
    public boolean DuplicatedNickname(String nickname) {
        Optional<DABOUser> user = userRepository.findDABOUserByNickname(nickname);
        if(user.isPresent()){
            return false;
        }
        return true;
    }

    // 이메일 중복 검사
    public boolean DuplicatedEmail(String email) {
        Optional<DABOUser> user = userRepository.findDABOUserByEmail(email);
        if(user.isPresent()){
            return false;
        }
        return true;
    }

    public DABOUser get() {
        DABOUser userTemp = commonService.getLoginUser();
        Optional<DABOUser> user = userRepository.findDABOUserByEmail(userTemp.getEmail());
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }

    @Transactional
    public DABOUser add(daboUserDto userDto) {
        Optional<DABOUser> sameEmail = userRepository.findDABOUserByEmail(userDto.getEmail());
//        if(sameEmail.isPresent()) throw new DuplicateRequestException();
        DABOUser dabouser = DABOUser.builder()
                .nickname(userDto.getNickname())
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .bloodType(userDto.getBloodType())
                .point(0L)
                .build();
        userRepository.save(dabouser);
        return dabouser;
    }

    @Transactional
    public DABOUser update(daboUserDto userDto) {
        DABOUser user = commonService.getLoginUser();

        Optional<DABOUser> found = userRepository.findDABOUserByEmail(user.getEmail());
        if(found == null)
            throw new ApplicationException("회원 정보를 찾을 수 없습니다.");
        DABOUser userUpdate = found.get();

        if(userDto.getPassword() != null) userUpdate.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if(userDto.getBloodType() != null) userUpdate.setBloodType(userDto.getBloodType());
        if(userDto.getNickname() != null) userUpdate.setNickname(userDto.getNickname());

        userRepository.save(userUpdate);

        return userUpdate;
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    public DABOUser getUserByEmail(String email) {
        Optional<DABOUser> found = userRepository.findDABOUserByEmail(email);
        if(found.isPresent()){
            return found.get();
        }
        throw new ApplicationException("회원 정보를 찾을 수 없습니다.");
    }

    public DABOUser resetPassword(String email) throws Exception{
        Optional<DABOUser> userTemp = userRepository.findDABOUserByEmail(email);
        System.out.println("userTemp = " + userTemp);
        if(userTemp.isPresent()){
            DABOUser user = userTemp.get();
            String newPassword = mailingService.sendSimpleMessage(email);
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return user;
        }
        throw new ApplicationException("회원 정보를 찾을 수 없습니다.");
    }
}


