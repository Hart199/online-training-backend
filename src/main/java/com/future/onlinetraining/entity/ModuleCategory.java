package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "module_categories")
public class ModuleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moduleCategory", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Module> modules;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moduleCategory", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ModuleRequest> moduleRequests;

}
