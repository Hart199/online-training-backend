package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private String materialDescription;

    private int timePerSession;

    private String status;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JsonBackReference
    private ModuleCategory moduleCategory;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module", cascade = CascadeType.REMOVE)
    private List<ModuleSession> moduleSessions;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    private List<ModuleRating> moduleRatings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    private List<Classroom> classrooms;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<ModuleMaterial> moduleMaterials;
}
