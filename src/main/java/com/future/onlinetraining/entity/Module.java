package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "modules")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private int version;

    private boolean hasExam;

    private int totalSession;

    private String materialDescription;

    private int timePerSession;

    private String status;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JsonIgnoreProperties(value = "modules")
    @ApiModelProperty(hidden = true)
    private ModuleCategory moduleCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "module")
    @ApiModelProperty(hidden = true)
    private List<ModuleRating> moduleRatings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = {"module", "classroomResults"})
    @ApiModelProperty(hidden = true)
    private List<Classroom> classrooms;

}
