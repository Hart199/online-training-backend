package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
import com.future.onlinetraining.users.model.User;
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
    private Module module;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "classroom")
    private List<ClassroomResult> classroomResults;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnoreProperties(value = "classroom")
    private List<ClassroomRequest> classroomRequests;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnoreProperties(value = "classroom")
    private List<ClassroomSession> classroomSessions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "classroom")
    private List<ClassroomMaterial> classroomMaterials;

    private Integer min_member;

    private Integer max_member;

    private String status;

}
