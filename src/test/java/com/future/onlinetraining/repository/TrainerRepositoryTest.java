package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.projection.TrainerData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerRepositoryTest {

    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    public void getTopTrainers_Test() {
        Page<TrainerData> trainers = trainerRepository.getTopTrainers(PageRequest.of(0, 5));
//        System.out.println(trainers.getContent());
        for (TrainerData trainerData : trainers.getContent()) {
            System.out.println(trainerData.getRating());
        }
    }
}