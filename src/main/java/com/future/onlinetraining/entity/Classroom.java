package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "classrooms")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User trainer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    @JsonIgnoreProperties(value = "classrooms")
    @ApiModelProperty(hidden = true)
    private Module module;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = {"classroom", "score"})
    @ApiModelProperty(hidden = true)
    private List<ClassroomResult> classroomResults;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnoreProperties(value = "classroom")
    @ApiModelProperty(hidden = true)
    private List<ClassroomRequest> classroomRequests;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnoreProperties(value = "classroom")
    @ApiModelProperty(hidden = true)
    private List<ClassroomSession> classroomSessions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "classroom")
    @ApiModelProperty(hidden = true)
    private List<ClassroomMaterial> classroomMaterials;

    private Integer min_member;

    private Integer max_member;

    private Double minScore;

    private String status;

    private boolean hasFinished = false;

}
