package com.test.quizApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Entity
public class User extends Auditable<String> implements Serializable {
    @Id
    private int id;
    private String name;
    private String email;
}
