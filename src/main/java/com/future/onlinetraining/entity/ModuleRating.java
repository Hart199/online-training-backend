package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
import com.future.onlinetraining.users.model.User;
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
@Table(name = "module_ratings")
public class ModuleRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "moduleRatings")
    @ApiModelProperty(hidden = true)
    private Module module;

    private double value;

    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    private User user;

}
