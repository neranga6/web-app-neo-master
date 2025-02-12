package com.webapp.neo.model;

import javax.persistence.*;


@Entity
@Table(name = "password_form")
public class PasswordForm {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "userName")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "roles")
    private String roles;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
    }


}
