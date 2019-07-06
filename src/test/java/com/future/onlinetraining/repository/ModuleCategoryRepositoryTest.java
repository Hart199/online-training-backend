package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.ModuleCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleCategoryRepositoryTest {
    @Autowired
    private ModuleCategoryRepository moduleCategoryRepository;

    @Test
    public void getAllModuleCategoryTest() {

        Assert.assertNotNull(moduleCategoryRepository.all());
        System.out.println(moduleCategoryRepository.all());

    }

}