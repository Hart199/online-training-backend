package com.future.onlinetraining.repository;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleCategory;
import com.future.onlinetraining.entity.ModuleSession;
import com.future.onlinetraining.entity.projection.ClassroomSubscribed;
import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
    private ModuleSessionRepository moduleSessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void getAllClassroomTest(){
        classroomRepository.deleteAll();
        moduleRepository.deleteAll();
        moduleSessionRepository.deleteAll();
        moduleCategoryRepository.deleteAll();

        ModuleCategory moduleCategory = ModuleCategory.builder()
                .name("cat1")
                .build();

        moduleCategory = moduleCategoryRepository.save(moduleCategory);

        ModuleSession moduleSession = ModuleSession.builder()
                .description("desc1")
                .isExam(false)
                .startTime(1000)
                .build();

        moduleSessionRepository.save(moduleSession);

        List<ModuleSession> moduleSessions = moduleSessionRepository.findAll();

        Module module = Module.builder()
                .name("module1")
                .timePerSession(60)
                .description("desc")
                .moduleCategory(moduleCategory)
                .moduleSessions(moduleSessions)
                .build();

        module = moduleRepository.save(module);

        Classroom classroom = Classroom.builder()
                .name("class1")
                .min_member(1)
                .max_member(5)
                .status("active")
                .module(module)
                .build();

        classroomRepository.save(classroom);

        Assert.assertNotNull(classroomRepository.findAll());
        System.out.println(classroomRepository.findAll());
        System.out.println(classroomRepository.findAll(PageRequest.of(0, 5)));

        classroomRepository.deleteAll();
        moduleRepository.deleteAll();
        moduleSessionRepository.deleteAll();
        moduleCategoryRepository.deleteAll();
    }


    @Test
    public void getSubscribedClassroomTest() {
        User user = userRepository.findByEmail("trainee@gmail.com");
        Page<ClassroomSubscribed> classroomSubscribedPage = classroomRepository.findSubscribed(
                PageRequest.of(0, 2), user.getId());

        for (ClassroomSubscribed classroomSubscribed : classroomSubscribedPage.getContent()) {
            System.out.println(classroomSubscribed.getName());
        }
    }
}