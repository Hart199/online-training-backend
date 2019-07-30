package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private int timePerSession;

    private String status;

    @ManyToOne(fetch =  FetchType.EAGER)
    private ModuleCategory moduleCategory;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module")
    private List<ModuleSession> moduleSessions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    @JsonIgnore
    private List<ModuleRating> moduleRatings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module")
    @JsonIgnore
    private List<Classroom> classrooms;
}
