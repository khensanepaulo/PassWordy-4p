package com.example.passwordy.password;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.transaction.Transactional;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class PasswordService {
    @Autowired
    private PasswordRepository passwordRepository;


    PasswordEncoder passwordEncoder;

    public PasswordService(PasswordRepository passwordRepository){
        this.passwordRepository = passwordRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Transactional
    public PasswordEntity save(PasswordEntity passwordEntity){
        return passwordRepository.save(passwordEntity);
    }
    public List<PasswordEntity> findAll() {
        return passwordRepository.findAll();
    }

    public Optional<PasswordEntity> findById(int id) {
        return passwordRepository.findById(id);
    }

//    public Optional<PasswordEntity> findByName(String name) {
//        return passwordRepository.findBy(name);
//    }

    @Transactional
    public void delete(PasswordEntity PasswordEntity) {
        passwordRepository.delete(PasswordEntity);
    }
}

