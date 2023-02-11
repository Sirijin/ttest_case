package com.example.demo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "survey")
@Getter
@Setter
@NoArgsConstructor
public class Survey {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String surveyName;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "answer")
    private Boolean answer;

    @OneToMany(mappedBy = "survey")
    private List<Question> questions;

    public Survey(String surveyName) {
        this.surveyName = surveyName;
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now().plusHours(1);
    }
}