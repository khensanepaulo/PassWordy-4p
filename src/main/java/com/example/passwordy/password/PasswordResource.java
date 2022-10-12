package com.example.passwordy.password;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/passwords")
public class PasswordResource {
    
    final PasswordService passwordService;
    
    @Autowired
    public PasswordResource(PasswordService passwordService) {this.passwordService = passwordService;}

    public PasswordEntity unencrypt(PasswordEntity passwordEntity){
        byte[] decoded = Base64.getDecoder().decode(passwordEntity.getSenha());
        String decodedString = new String(decoded);
        passwordEntity.setSenha(decodedString);

        return passwordEntity;
    }

    @GetMapping()
    public ResponseEntity<List<PasswordEntity>> getAllRequest() {
        return ResponseEntity.status(HttpStatus.OK).body(passwordService.findAll().stream().map(this::unencrypt).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Object> saveRequest(@RequestBody @Valid PasswordDto passwordDto) {
        var requestEntity = new PasswordEntity();
        String original = passwordDto.getSenha();
        String encoded = Base64.getEncoder().encodeToString(original.getBytes());
        passwordDto.setSenha(encoded);
        BeanUtils.copyProperties(passwordDto, requestEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(passwordService.save(requestEntity));
    }



    @GetMapping("/{id}")
    public ResponseEntity<Object> getOnerequest(@PathVariable(value = "id") int id) {
        Optional<PasswordEntity> requestEntityOptional = passwordService.findById(id);
        if (!requestEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("request not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(requestEntityOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleterequest(@PathVariable(value = "id") int id) {
        Optional<PasswordEntity> requestEntityOptional = passwordService.findById(id);
        if (!requestEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("request not found.");
        }
        passwordService.delete(requestEntityOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("request deleted sucessfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updaterequest(@PathVariable(value = "id") int id,
                                                @RequestBody @Valid PasswordDto passwordDto) {
        Optional<PasswordEntity> requestEntityOptional = passwordService.findById(id);
        if (!requestEntityOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("request not found.");
        }
        var requestEntity = new PasswordEntity();
        BeanUtils.copyProperties(passwordDto, requestEntity);
        requestEntity.setId(requestEntityOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(passwordService.save(requestEntity));
    }


}
