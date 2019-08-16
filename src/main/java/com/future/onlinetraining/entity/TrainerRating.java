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
@Table(name = "trainer_ratings")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TrainerRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnoreProperties(value = "trainerRatings")
    @ApiModelProperty(hidden = true)
    private User trainer;

    @ManyToOne
    @JsonIgnoreProperties(value = "trainerRatings")
    @ApiModelProperty(hidden = true)
    private  User user;

    private double value;

    private String comment;

}
