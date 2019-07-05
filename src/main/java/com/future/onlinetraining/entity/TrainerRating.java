package com.future.onlinetraining.entity;

import com.future.onlinetraining.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "trainer_ratings")
public class TrainerRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User trainer;

    @ManyToOne
    private  User user;

    private double value;

    private String comment;

}
