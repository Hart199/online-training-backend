package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "moduleCategory", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "moduleCategory")
    private List<Module> modules;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "moduleCategory", cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties(value = "moduleCategory")
    private List<ModuleRequest> moduleRequests;

}
