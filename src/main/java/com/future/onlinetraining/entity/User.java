package com.future.onlinetraining.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "Kolom email masih kosong.")
    private String email;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String fullname;

    @Column
    private String phone;

    @Column
    private String photo;

    @Column
    private String status;

    @OneToOne
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"user", "trainer"})
    @LazyCollection(LazyCollectionOption.FALSE)
//    @JsonIgnore
    private List<TrainerRating> trainerRatings;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ClassroomRequest> classroomRequests;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ClassroomResult> classroomResult;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ModuleRequest> moduleRequests;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add( new SimpleGrantedAuthority(this.getRole().getValue()));
        return grantedAuthorities;
    }

    @Override
    public String getUsername(){
        return this.getEmail();
    }

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    public boolean isEnabled(){
        return true;
    }

    public boolean isAccountNonExpired(){
        return true;
    }

    public boolean isAccountNonLocked(){
        return true;
    }

    public boolean isCredentialsNonExpired(){
        return true;
    }
}
