package com.future.onlinetraining.utility;

import com.future.onlinetraining.entity.*;
import com.future.onlinetraining.repository.*;
import com.future.onlinetraining.users.model.Role;
import com.future.onlinetraining.users.model.User;
import com.future.onlinetraining.users.repository.RoleRepository;
import com.future.onlinetraining.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModuleCategoryRepository moduleCategoryRepository;
    @Autowired
    ModuleRepository moduleRepository;
    @Autowired
    ClassroomRepository classroomRepository;
    @Autowired
    ClassroomRequestRepository classroomRequestRepository;
    @Autowired
    ClassroomResultRepository classroomResultRepository;
    @Autowired
    ModuleMaterialRepository moduleMaterialRepository;
    @Autowired
    ModuleRatingRepository moduleRatingRepository;
    @Autowired
    ModuleRequestRepository moduleRequestRepository;
    @Autowired
    ClassroomSessionRepository classroomSessionRepository;
    @Autowired
    TrainerRatingRepository trainerRatingRepository;
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    ModuleRequestLikeRepository moduleRequestLikeRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

//    @EventListener
    public User initRoleAndAdminAccount(){

        Role role = roleRepository.findByValue("ADMIN");

        if(role == null){
            roleRepository.save(Role.builder().value("ADMIN").build());
            role = roleRepository.findByValue("ADMIN");
        }

        User user = userRepository.findByEmail("root@gmail.com");

        if(user == null){
            return userRepository.save(User.builder()
                    .email("root@gmail.com")
                    .password(encoder.encode("root"))
                    .role(role)
                    .fullname("root")
                    .build());
        }

        return user;
    }

    @EventListener
    public void generateData(ContextRefreshedEvent event) {
        User user = this.initRoleAndAdminAccount();

        Role trainerRole = roleRepository.save(Role.builder().value("TRAINER").build());
        Role traineeRole = roleRepository.save(Role.builder().value("TRAINEE").build());

        ModuleCategory moduleCategory = moduleCategoryRepository.save(
                ModuleCategory.builder()
                .name("Backend Development")
                .build()
        );

        User trainer = User.builder()
                .email("trainer@gmail.com")
                .fullname("Trainer Mantaap")
                .password(encoder.encode("trainer123"))
                .role(trainerRole)
                .description("Seorang trainer yang sangat mantap")
                .phone("081234567890")
                .build();
        trainer = userRepository.save(trainer);

        User trainee = User.builder()
                .email("trainee@gmail.com")
                .fullname("Trainee Istiqomah")
                .password(encoder.encode("trainee123"))
                .role(traineeRole)
                .description("Seorang trainee yang ingin belajar")
                .phone("080987654321")
                .build();
        trainee = userRepository.save(trainee);

        TrainerRating trainerRating = TrainerRating.builder()
                .trainer(trainer)
                .user(trainee)
                .comment("Mantaap")
                .value(5)
                .build();
        trainerRatingRepository.save(trainerRating);

        trainerRating = TrainerRating.builder()
                .trainer(trainer)
                .user(trainee)
                .comment("gud")
                .value(4.5)
                .build();
        trainerRatingRepository.save(trainerRating);

        Module module = Module.builder()
                .moduleCategory(moduleCategory)
                .name("Tutorial Spring Boot 1")
                .timePerSession(60)
                .description("Dasar Spring Boot")
                .status("open")
                .build();
        module = moduleRepository.save(module);
        moduleRepository.save(Module.builder()
                .moduleCategory(moduleCategory)
                .name("Tutorial Spring Boot 2")
                .timePerSession(60)
                .description("Dasar Spring Boot")
                .status("closed")
                .build());

        ModuleMaterial moduleMaterial = ModuleMaterial.builder()
                .module(module)
                .description("Basic Java")
                .file("java.pdf")
                .build();
        moduleMaterial = moduleMaterialRepository.save(moduleMaterial);

        ModuleRating moduleRating = ModuleRating.builder()
                .module(module)
                .comment("mantaap")
                .value(5)
                .build();
        moduleRating = moduleRatingRepository.save(moduleRating);

        Classroom classroom = Classroom.builder()
                .max_member(30)
                .min_member(1)
                .module(module)
                .status("open")
                .name("Kelas Spring Boot 01")
                .trainer(trainer)
                .build();
        classroom = classroomRepository.save(classroom);

        ClassroomSession classroomSession1 = ClassroomSession.builder()
                .description("Sesi 1")
                .startTime(1000)
                .isExam(false)
                .classroom(classroom)
                .build();
        classroomSession1 = classroomSessionRepository.save(classroomSession1);

        ClassroomSession classroomSession2 = ClassroomSession.builder()
                .description("Sesi 2")
                .startTime(2000)
                .isExam(true)
                .classroom(classroom)
                .build();
        classroomSession2 = classroomSessionRepository.save(classroomSession2);

        ClassroomResult classroomResult = ClassroomResult.builder()
                .classroom(classroom)
                .status("pending")
                .user(trainee)
                .score(0)
                .build();
        classroomResult = classroomResultRepository.save(classroomResult);

        moduleRequestRepository.save(
                ModuleRequest.builder()
                .moduleCategory(moduleCategory)
                .user(trainee)
                .title("Deep Learning")
                .build()
        );

        classroomRequestRepository.save(
                ClassroomRequest.builder()
                .classroom(classroom)
                .user(trainee)
                .status("waiting")
                .build()
        );

        classroomRequestRepository.save(
                ClassroomRequest.builder()
                        .classroom(classroom)
                        .user(trainer)
                        .status("waiting")
                        .build()
        );

    }
}