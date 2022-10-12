package com.example.passwordy.password;

import lombok.Data;
import org.springframework.security.core.userdetails.User;

@Data
public class PasswordDto {
    private Integer id;
    private String senha;
    private String descricao;
    private String url;
    //private User user;
}
