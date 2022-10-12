package com.example.passwordy.password;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;

@Entity
@Data
@Table(name="senhas")
public class PasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, length = 45)
    private String descricao;

    @Column(nullable = false, length = 45)
    private String url;
//
//    @Column(nullable = false, length = 45)
//    private User user;
}
