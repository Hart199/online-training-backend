package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "module_requests")
public class ModuleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "moduleRequests")
    @ApiModelProperty(hidden = true)
    private ModuleCategory moduleCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @ApiModelProperty(hidden = true)
    private User user;

    @OneToMany(mappedBy = "moduleRequest", cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnoreProperties(value = "moduleRequest")
    @ApiModelProperty(hidden = true)
    private List<ModuleRequestLike> moduleRequestLikes;

    private String status;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

}
