package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classroom_results")
public class ClassroomResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "classroomResults")
    @ApiModelProperty(hidden = true)
    private Classroom classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "trainerRatings")
    @ApiModelProperty(hidden = true)
    private User user;

    private double score;

    private String status;

}
