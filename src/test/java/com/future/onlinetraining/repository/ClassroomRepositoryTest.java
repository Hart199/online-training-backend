package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.User;
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
public class ClassroomRepositoryTest {

    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ModuleCategoryRepository moduleCategoryRepository;
    @Autowired
    private ClassroomSessionRepository classroomSessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllClassroomTest(){

        Assert.assertNotNull(classroomRepository.findAll());
        System.out.println(classroomRepository.findAll(PageRequest.of(0, 5)));
    }


    @Test
    public void getSubscribedClassroomTest() {
        User user = userRepository.findByEmail("trainee@gmail.com");
        Page<Classroom> classroomSubscribedPage = classroomRepository.findSubscribed(
                PageRequest.of(0, 2), user.getId(), null);

        for (Classroom classroomSubscribed : classroomSubscribedPage.getContent()) {
            System.out.println(classroomSubscribed.getName());
        }
    }
}