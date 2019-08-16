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
@Table(name = "module_request_likes")
public class ModuleRequestLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "moduleRequestLikes")
    @ApiModelProperty(hidden = true)
    private ModuleRequest moduleRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(hidden = true)
    private User user;

}


