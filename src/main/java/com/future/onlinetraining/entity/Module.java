package com.future.onlinetraining.entity;

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

    @ManyToOne(fetch =  FetchType.EAGER)
    private ModuleCategory moduleCategory;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "module")
    private List<ModuleSession> moduleSessions;
}
