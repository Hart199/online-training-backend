package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleRepositoryTest {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleCategoryRepository moduleCategoryRepository;

    @Test
    public void getAllModuleTestWithPage(){
//        ModuleCategory moduleCategory = ModuleCategory.builder()
//                .name("Machine Learning")
//                .build();
//
//        Module module1 = Module.builder()
//                .name("Machine Learning 1")
//                .moduleCategory(moduleCategoryRepository.save(moduleCategory))
//                .timePerSession(60)
//                .build();
//
//        moduleRepository.save(module1);
        Page<Module> modulePage = moduleRepository.all(PageRequest.of(0, 5));
//        Assert.assertNotNull(module);
//        System.out.println(module);

        for(Module module : modulePage.getContent()) {
            System.out.println(module.getName());
        }
//        moduleRepository.deleteAll();
//        moduleCategoryRepository.deleteAll();
    }
}