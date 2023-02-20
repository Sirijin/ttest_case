package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "question")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Question {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Text should not be empty")
    @Column(name = "text", nullable = false)
    private String text;

    @NotNull(message = "Question should have its order")
    @Min(value = 0, message = "Question's order cant be less than 0")
    @Column(name = "some_order", nullable = false)
    private Integer someOrder;

    @ManyToOne
    @JoinColumn(name = "survey_id", referencedColumnName = "id")
    @ToString.Exclude
    private Survey survey;

}
