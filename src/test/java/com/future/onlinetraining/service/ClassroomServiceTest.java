package com.future.onlinetraining.service;

import com.future.onlinetraining.dto.ClassroomDTO;
import com.future.onlinetraining.dto.ClassroomDetailDTO;
import com.future.onlinetraining.dto.ModuleClassroomDTO;
import com.future.onlinetraining.entity.*;
import com.future.onlinetraining.entity.projection.ClassroomData;
import com.future.onlinetraining.entity.projection.ClassroomDetailData;
import com.future.onlinetraining.repository.*;
import com.future.onlinetraining.service.impl.ClassroomServiceImpl;
import com.future.onlinetraining.entity.Role;
import com.future.onlinetraining.entity.User;
import com.future.onlinetraining.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassroomServiceTest {

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @MockBean
    private ClassroomRepository classroomRepository;
    @MockBean
    private ModuleRepository moduleRepository;
    @MockBean
    private ClassroomSessionRepository classroomSessionRepository;
    @MockBean
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @MockBean
    private ModuleRequestRepository moduleRequestRepository;
    @MockBean
    private ModuleCategoryRepository moduleCategoryRepository;
    @Spy
    private ClassroomRequestRepository classroomRequestRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    public Module getModuleMock() {
        Module module = Module.builder()
                .timePerSession(60)
                .hasExam(true)
                .totalSession(1)
                .version(1)
                .name("module1")
                .build();
        return module;
    }

    public Classroom getClassroomMock() {
        Classroom classroom = Classroom.builder()
                .name("class1")
                .min_member(1)
                .max_member(5)
                .status("active")
                .module(getModuleMock())
                .build();
        return classroom;
    }

    public User getTrainerMock() {
        User user = User.builder()
                .email("trainer@gmail.com")
                .fullname("trainer mantaap")
                .role(Role.builder().value("TRAINER").build())
                .phone("0817152308")
                .build();
        return user;
    }

    @Test
    public void getAllPageableClassroom_Test() {
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<Classroom> classroomList = new ArrayList<>();
        classroomList.add(getClassroomMock());
        Page<Classroom> classroomPage = new PageImpl<>(classroomList, pageRequest, classroomList.size());

        when(classroomRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(classroomPage);

        Page<Classroom> results = classroomService.getAllPageableClassroom();

        Assert.assertEquals(classroomPage.getContent(), results.getContent());
        Mockito.verify(classroomRepository).findAll(Mockito.any(PageRequest.class));

    }

    @Test
    public void getAllSubscribed_Test() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Classroom> classroomPage = Page.empty(pageRequest);

        when(userService.getUserFromSession()).thenReturn(getTrainerMock());
        when(classroomRepository.findSubscribed(
                Mockito.any(PageRequest.class), Mockito.anyInt(), Mockito.anyString())).thenReturn(Page.empty(pageRequest));

        Page<Classroom> results = classroomService.getAllSubscribed(0, 5, "");

        Assert.assertEquals(classroomPage, results);
        Mockito.verify(classroomRepository).findSubscribed(Mockito.any(PageRequest.class), Mockito.anyInt(), Mockito.anyString());
    }

    @Test
    public void getAll_Test() {
        List classrooms = new ArrayList();
        classrooms.add(getClassroomMock());

        Page<Classroom> classroomPage = new PageImpl<>(
                classrooms, PageRequest.of(0, 5), classrooms.size());

        when(classroomRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(classroomPage);

        Page<Classroom> results = classroomService.getAll(PageRequest.of(0, 5));

        Assert.assertEquals(results, classroomPage);
    }

    @Test
    public void all_Test() {
        List classrooms = new ArrayList();
        classrooms.add(new ClassroomData());

        Page<ClassroomData> classroomDataPage = new PageImpl<>(
                classrooms, PageRequest.of(0, 5), classrooms.size());

        when(classroomRepository.all(Mockito.any(PageRequest.class), Mockito.anyString(), Mockito.anyBoolean()))
                .thenReturn(classroomDataPage);

        Page<ClassroomData> results = classroomService.all("", true, PageRequest.of(0, 5));

        Assert.assertEquals(results, classroomDataPage);
    }

    @Test(expected = RuntimeException.class)
    public void verifyClassroomSessionOnModuleWhenSessionNotEqual_Test() {
        List classroomSessions = new ArrayList();

        Module module = Module.builder().totalSession(1).build();

        classroomService.verifyClassroomSessionOnModule(module, classroomSessions);
    }

    @Test(expected = RuntimeException.class)
    public void verifyClassroomSessionOnModuleWhenHasExamNotEqual_Test() {
        List classroomSessions = new ArrayList();
        classroomSessions.add(
                ClassroomSession.builder()
                .isExam(true)
                .build()
        );

        Module module = Module.builder().totalSession(1).hasExam(false).build();

        classroomService.verifyClassroomSessionOnModule(module, classroomSessions);
    }

    @Test
    public void createWhenModuleIsNull_Test() {
        ClassroomDTO classroomDTO = new ClassroomDTO();
        when(userService.getUserFromSession()).thenReturn(getTrainerMock());
        when(moduleRepository.getOne(Mockito.anyInt())).thenReturn(null);

        Assert.assertEquals(classroomService.create(classroomDTO), null);
    }

    @Test
    public void create_Test() {
        Classroom classroom = new Classroom();
        ClassroomDTO classroomDTO = new ClassroomDTO();
        List classroomSessions = new ArrayList();
        classroomSessions.add(
                ClassroomSession.builder()
                        .isExam(true)
                        .build()
        );
        Module module = Module.builder().totalSession(1).hasExam(true).build();
        classroomDTO.setClassroomSessions(classroomSessions);
        classroomDTO.setRefClassroomId(1);

        when(userService.getUserFromSession()).thenReturn(getTrainerMock());
        when(moduleRepository.getOne(Mockito.anyInt())).thenReturn(module);

        when(classroomSessionRepository.saveAll(Mockito.any(ArrayList.class))).thenReturn(new ArrayList<ClassroomSession>());

        when(classroomRepository.save(Mockito.any(Classroom.class))).thenReturn(classroom);
        doNothing().when(classroomRequestRepository).deleteAllByClassroomId(Mockito.anyInt());

        Classroom result = classroomService.create(classroomDTO);

        Assert.assertEquals(classroom, result);
    }

    @Test
    public void createModuleAndClassroom_WhenModuleCategoryIsNull_Test() {
        ModuleClassroomDTO moduleClassroomDTO = new ModuleClassroomDTO();
        ModuleClassroomDTO.ModuleDTO moduleDTO = Mockito.mock(ModuleClassroomDTO.ModuleDTO.class);
        moduleClassroomDTO.setModule(moduleDTO);

        when(moduleCategoryRepository.findByName(Mockito.any())).thenReturn(null);
        Assert.assertEquals(
                classroomService.createModuleAndClassroom(moduleClassroomDTO), null);
    }

    @Test
    public void createModuleAndClassroom_WhenTrainerIsNull_Test() {
        ModuleClassroomDTO moduleClassroomDTO = new ModuleClassroomDTO();
        ModuleClassroomDTO.ModuleDTO moduleDTO = Mockito.mock(ModuleClassroomDTO.ModuleDTO.class);
        moduleClassroomDTO.setModule(moduleDTO);
        moduleClassroomDTO.setClassroom(Mockito.mock(ModuleClassroomDTO.Classroom.class));
        moduleClassroomDTO.setModuleRequestId(1);

        when(moduleCategoryRepository.findByName(Mockito.any())).thenReturn(Mockito.mock(ModuleCategory.class));
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        when(moduleRepository.findById(Mockito.anyInt())).thenReturn(null);

        Assert.assertEquals(
                classroomService.createModuleAndClassroom(moduleClassroomDTO), null);
    }

    @Test
    public void getClassroomDetail_WhenClassroomIsNull_Test() {
        when(classroomRepository.getDetail(Mockito.anyInt())).thenReturn(null);

        Assert.assertEquals(classroomService.getClassroomDetail(1), null);
    }

    @Test
    public void getClassroomDetail_Test() {
        ClassroomDetailData classroomDetailData = Mockito.mock(ClassroomDetailData.class);
        when(classroomRepository.getDetail(Mockito.anyInt())).thenReturn(classroomDetailData);

        Assert.assertEquals(classroomService.getClassroomDetail(1), classroomDetailData);
    }

    @Test
    public void editDetail_WhenClassroomIsNull_Test() {
        when(classroomRepository.find(Mockito.anyInt())).thenReturn(null);
        Assert.assertEquals(classroomService.editDetail(1, Mockito.mock(ClassroomDetailDTO.class)), null);
    }

    @Test
    public void editDetail_WhenTrainerIsNull_Test() {
        when(classroomRepository.find(Mockito.anyInt())).thenReturn(Mockito.mock(Classroom.class));
        when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Assert.assertEquals(classroomService.editDetail(1, Mockito.mock(ClassroomDetailDTO.class)), null);
    }

    @Test
    public void editDetail_WhenClassroomSessionsIsNull_Test() {
        when(classroomRepository.find(Mockito.anyInt())).thenReturn(Mockito.mock(Classroom.class));
        when(userRepository.findByEmail("trainer@gmail.com")).thenReturn(Mockito.mock(User.class));

        Classroom classroom = new Classroom();
        when(classroomRepository.save(Mockito.any(Classroom.class))).thenReturn(classroom);

        ClassroomDetailDTO classroomDetailDTO = new ClassroomDetailDTO();
        classroomDetailDTO.setTrainerEmail("trainer@gmail.com");
        Assert.assertEquals(classroomService.editDetail(1, classroomDetailDTO), classroom);
    }

    @Test(expected = NullPointerException.class)
    public void join_WhenUserIsNull_Test() {
        when(userService.getUserFromSession()).thenReturn(null);
        classroomService.join(1);
    }

    @Test(expected = NullPointerException.class)
    public void join_WhenClassroomOptionalIsNotPresent_Test() {
        when(userService.getUserFromSession()).thenReturn(Mockito.mock(User.class));
        when(classroomRepository.findById(Mockito.anyInt())).thenReturn(null);
        classroomService.join(1);
    }

//    @Test
//    public void createModuleAndClassroom_Test() {
//        ModuleClassroomDTO moduleClassroomDTO = new ModuleClassroomDTO();
//        ModuleClassroomDTO.ModuleDTO moduleDTO = Mockito.mock(ModuleClassroomDTO.ModuleDTO.class);
//        moduleClassroomDTO.setModule(moduleDTO);
//        moduleClassroomDTO.setClassroom(Mockito.mock(ModuleClassroomDTO.Classroom.class));
//        moduleClassroomDTO.setModuleRequestId(1);
//
//        when(moduleCategoryRepository.findByName(Mockito.any())).thenReturn(Mockito.mock(ModuleCategory.class));
//
//        Optional<ModuleRequest> moduleRequestOptional = Optional.of(Mockito.mock(ModuleRequest.class));
//        when(moduleRequestRepository.findById(Mockito.anyInt())).thenReturn(moduleRequestOptional);
//
////        Module m = Mockito.mock(Module.class, RETURNS_DEEP_STUBS);
////        when(Module.builder()).thenReturn(Module.builder());
////        when(Module.builder().build()).thenReturn(Mockito.mock(Module.class));
//
//        List classroomSessions = new ArrayList();
//        classroomSessions.add(
//                ClassroomSession.builder()
//                        .isExam(true)
//                        .build()
//        );
//        Module module = Module.builder().totalSession(1).hasExam(true).build();
//        moduleClassroomDTO.getClassroom().setClassroomSessions(classroomSessions);
//        moduleClassroomDTO.getClassroom().setTrainerEmail("trainer@gmail.com");
////        classroomDTO.setClassroomSessions(classroomSessions);
//
//        when(moduleRepository.save(Mockito.any(Module.class))).thenReturn(module);
//        when(classroomSessionRepository.saveAll(Mockito.anyIterable())).thenReturn(Mockito.mock(List.class));
//        when(moduleRequestRepository.save(Mockito.any(ModuleRequest.class))).thenReturn(null);
////        when(userRepository.findByEmail("trainer@gmail.com")).thenReturn(Mockito.mock(User.class));
////
//        Classroom classroom = new Classroom();
//        when(classroomRepository.save(Mockito.any(Classroom.class))).thenReturn(classroom);
//
//        Assert.assertEquals(
//                classroomService.createModuleAndClassroom(moduleClassroomDTO), classroom);
//    }



//    @After
//    public void tearDown(){
//        verifyNoMoreInteractions(classroomRepository);
//    }
}