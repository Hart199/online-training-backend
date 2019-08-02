package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Integer.class)
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private int version;

    private boolean hasExam;

    private String materialDescription;

    private int timePerSession;

    private String status;

    @ManyToOne(fetch =  FetchType.EAGER)
    private ModuleCategory moduleCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    private List<ModuleRating> moduleRatings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "module", cascade = CascadeType.REMOVE)
    private List<Classroom> classrooms;

}
