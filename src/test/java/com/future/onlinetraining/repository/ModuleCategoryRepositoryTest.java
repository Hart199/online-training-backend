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
        ModuleCategory moduleCategory1 = ModuleCategory.builder()
                .name("Machine Learning")
                .build();

        ModuleCategory moduleCategory2 = ModuleCategory.builder()
                .name("Software Engineering")
                .build();

        moduleCategoryRepository.save(moduleCategory1);
        moduleCategoryRepository.save(moduleCategory2);

        Assert.assertNotNull(moduleCategoryRepository.all());
        System.out.println(moduleCategoryRepository.all());

        moduleCategoryRepository.deleteAll();
    }

}