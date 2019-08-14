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

import java.sql.Timestamp;
import java.time.Instant;

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
    ClassroomMaterialRepository classroomMaterialRepository;
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
        ModuleCategory moduleCategory2 = moduleCategoryRepository.save(
                ModuleCategory.builder()
                        .name("Artificial Intellegent")
                        .build()
        );
        ModuleCategory moduleCategory3 = moduleCategoryRepository.save(
                ModuleCategory.builder()
                        .name("Data Visualization")
                        .build()
        );

        User trainer = User.builder()
                .email("trainer@gmail.com")
                .fullname("Trainer Mantaap")
                .password(encoder.encode("trainer123"))
                .role(trainerRole)
                .phone("081234567890")
                .build();
        trainer = userRepository.save(trainer);

        User trainee = User.builder()
                .email("trainee@gmail.com")
                .fullname("Trainee Istiqomah")
                .password(encoder.encode("trainee123"))
                .role(traineeRole)
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

//      MODULE
        Module module = Module.builder()
                .moduleCategory(moduleCategory)
                .name("Tutorial Spring Boot 1")
                .timePerSession(60)
                .description("Dasar Spring Boot")
                .status("open")
                .version(1)
                .totalSession(2)
                .hasExam(true)
                .build();
        module = moduleRepository.save(module);
        Module module2 = moduleRepository.save(Module.builder()
                .moduleCategory(moduleCategory)
                .name("Tutorial Spring Boot 2")
                .timePerSession(60)
                .description("Dasar Spring Boot")
                .status("closed")
                .totalSession(2)
                .version(1)
                .hasExam(true)
                .build());
        Module module3 = moduleRepository.save(Module.builder()
                .moduleCategory(moduleCategory2)
                .name("Machine Learning Dasar")
                .timePerSession(90)
                .description("Machine learning, bidang ilmu komputer yang memberikan kemampuan sistem komputer untuk belajar dari data, adalah salah satu topik terpanas dalam ilmu komputer.")
                .status("open")
                .totalSession(3)
                .version(1)
                .hasExam(false)
                .build());
        Module module4 = moduleRepository.save(Module.builder()
                .moduleCategory(moduleCategory3)
                .name("Data Visualization in Python")
                .timePerSession(45)
                .description("Learn how to present data graphically with Python, Matplotlib, and Seaborn.")
                .status("open")
                .totalSession(2)
                .version(1)
                .hasExam(true)
                .build());

//      MODULE RATING
        ModuleRating moduleRating = ModuleRating.builder()
                .module(module)
                .comment("mantaap")
                .value(5)
                .build();
        moduleRating = moduleRatingRepository.save(moduleRating);
        moduleRatingRepository.save(ModuleRating.builder()
                .module(module2)
                .comment("mantaap")
                .value(4.5)
                .build());
        moduleRatingRepository.save(ModuleRating.builder()
                .module(module3)
                .comment("mantaap")
                .value(4)
                .build());
        moduleRatingRepository.save(ModuleRating.builder()
                .module(module4)
                .comment("kurang mantaap")
                .value(3.5)
                .build());

//      CLASSROOM
        Classroom classroom = Classroom.builder()
                .max_member(30)
                .min_member(10)
                .module(module)
                .status("open")
                .name("Kelas Spring Boot 01")
                .trainer(trainer)
                .build();
        classroom = classroomRepository.save(classroom);
        Classroom classroom2 = classroomRepository.save(Classroom.builder()
                .max_member(25)
                .min_member(10)
                .module(module2)
                .status("close")
                .name("Kelas Spring Boot 02")
                .trainer(trainer)
                .build());
        Classroom classroom3 = classroomRepository.save(Classroom.builder()
                .max_member(50)
                .min_member(10)
                .module(module3)
                .status("open")
                .name("Kelas Unsupervised Learning 1")
                .trainer(trainer)
                .build());
        Classroom classroom4 = classroomRepository.save(Classroom.builder()
                .max_member(50)
                .min_member(10)
                .module(module3)
                .status("open")
                .name("Kelas Unsupervised Learning 2")
                .trainer(trainer)
                .build());
        Classroom classroom5 = classroomRepository.save(Classroom.builder()
                .max_member(30)
                .min_member(5)
                .module(module4)
                .status("open")
                .name("Kelas Visdat 01")
                .trainer(trainer)
                .build());

//      CLASSROOM SESSION
//      classroom session -> classrom
        ClassroomSession classroomSession11 = ClassroomSession.builder()
                .description("Sesi 1")
                .startTime(new Timestamp(System.currentTimeMillis()))
                .isExam(false)
                .classroom(classroom)
                .build();
        classroomSession11 = classroomSessionRepository.save(classroomSession11);
        ClassroomSession classroomSession12 = ClassroomSession.builder()
                .description("Sesi 2")
                .startTime(new Timestamp(System.currentTimeMillis()))
                .isExam(true)
                .classroom(classroom)
                .build();
        classroomSession12 = classroomSessionRepository.save(classroomSession12);

//      classroom session -> classrom2
        ClassroomSession classroomSession21 = ClassroomSession.builder()
                .description("Sesi 1")
                .startTime(new Timestamp(2000))
                .isExam(true)
                .classroom(classroom2)
                .build();
        classroomSession21 = classroomSessionRepository.save(classroomSession21);
        ClassroomSession classroomSession22 = ClassroomSession.builder()
                .description("Sesi 2")
                .startTime(new Timestamp(2000))
                .isExam(true)
                .classroom(classroom2)
                .build();
        classroomSession22 = classroomSessionRepository.save(classroomSession22);

//      classroom session -> classrom3
        ClassroomSession classroomSession31 = ClassroomSession.builder()
                .description("Sesi 1")
                .startTime(new Timestamp(3000))
                .isExam(false)
                .classroom(classroom3)
                .build();
        classroomSession31 = classroomSessionRepository.save(classroomSession31);
        ClassroomSession classroomSession32 = ClassroomSession.builder()
                .description("Sesi 2")
                .startTime(new Timestamp(3000))
                .isExam(false)
                .classroom(classroom3)
                .build();
        classroomSession32 = classroomSessionRepository.save(classroomSession32);
        ClassroomSession classroomSession33 = ClassroomSession.builder()
                .description("Sesi 3")
                .startTime(new Timestamp(3000))
                .isExam(false)
                .classroom(classroom3)
                .build();
        classroomSession33 = classroomSessionRepository.save(classroomSession33);

//      classroom session -> classrom4
        ClassroomSession classroomSession41 = ClassroomSession.builder()
                .description("Sesi 1")
                .startTime(new Timestamp(4000))
                .isExam(false)
                .classroom(classroom4)
                .build();
        classroomSession41 = classroomSessionRepository.save(classroomSession41);
        ClassroomSession classroomSession42 = ClassroomSession.builder()
                .description("Sesi 2")
                .startTime(new Timestamp(4000))
                .isExam(false)
                .classroom(classroom4)
                .build();
        classroomSession42 = classroomSessionRepository.save(classroomSession42);
        ClassroomSession classroomSession43 = ClassroomSession.builder()
                .description("Sesi 3")
                .startTime(new Timestamp(4000))
                .isExam(false)
                .classroom(classroom4)
                .build();
        classroomSession43 = classroomSessionRepository.save(classroomSession43);

//      classroom session -> classrom5
        ClassroomSession classroomSession51 = ClassroomSession.builder()
                .description("Sesi 1")
                .startTime(new Timestamp(5000))
                .isExam(false)
                .classroom(classroom5)
                .build();
        classroomSession51 = classroomSessionRepository.save(classroomSession51);
        ClassroomSession classroomSession52 = ClassroomSession.builder()
                .description("Sesi 2")
                .startTime(new Timestamp(5000))
                .isExam(true)
                .classroom(classroom5)
                .build();
        classroomSession52 = classroomSessionRepository.save(classroomSession52);

//      CLASS ROOM MATERIAL
        ClassroomMaterial classroomMaterial = ClassroomMaterial.builder()
                .classroom(classroom)
                .description("Basic Java")
                .file("java.pdf")
                .build();
        classroomMaterial = classroomMaterialRepository.save(classroomMaterial);
        classroomMaterialRepository.save(ClassroomMaterial.builder()
                .classroom(classroom2)
                .description("Basic Java")
                .file("java_Spring_Boot.pdf")
                .build());
        classroomMaterialRepository.save(ClassroomMaterial.builder()
                .classroom(classroom3)
                .description("UnLe Dasar")
                .file("unsupervised_learning_beginners.pdf")
                .build());
        classroomMaterialRepository.save(ClassroomMaterial.builder()
                .classroom(classroom4)
                .description("UnLe Dasar")
                .file("unsupervised_learning_beginners.pdf")
                .build());
        classroomMaterialRepository.save(ClassroomMaterial.builder()
                .classroom(classroom5)
                .description("Visdar")
                .file("data_visualization_by_Steve.pdf")
                .build());

//      CLASSROOM RESULT
        ClassroomResult classroomResult = ClassroomResult.builder()
                .classroom(classroom)
                .status("pending")
                .user(trainee)
                .score(0)
                .build();
        classroomResult = classroomResultRepository.save(classroomResult);
        classroomResultRepository.save(ClassroomResult.builder()
                .classroom(classroom2)
                .status("done")
                .user(trainee)
                .score(9)
                .build());

        moduleRequestRepository.save(
                ModuleRequest.builder()
                .moduleCategory(moduleCategory)
                .user(trainee)
                .title("Deep Learning")
                .status("waiting")
                .build()
        );
        moduleRequestRepository.save(
                ModuleRequest.builder()
                .moduleCategory(moduleCategory2)
                .user(trainee)
                .title("Min-Max and Deep Search Method")
                .status("waiting")
                .build()
        );

        classroomRequestRepository.save(
                ClassroomRequest.builder()
                .classroom(classroom2)
                .user(trainee)
                .status("waiting")
                .build()
        );

        classroomRequestRepository.save(
                ClassroomRequest.builder()
                .classroom(classroom2)
                .user(trainer)
                .status("waiting")
                .build()
        );

    }
}