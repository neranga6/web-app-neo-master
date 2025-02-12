package com.webapp.neo.model;

import javax.persistence.*;

@Entity
@Table(name = "resume_form")
public class ResumeForm {

    @Id
    @GeneratedValue
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
