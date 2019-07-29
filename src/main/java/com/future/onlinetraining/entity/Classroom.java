package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn
    private User trainer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn
    private Module module;

    @OneToMany(mappedBy = "classroom")
    @JsonBackReference
    private List<ClassroomResult> classroomResults;

    @OneToMany(mappedBy = "classroom")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonBackReference
    private List<ClassroomRequest> classroomRequests;

    private int min_member;

    private int max_member;

    private String status;

}
