package com.future.onlinetraining.service;

import com.future.onlinetraining.entity.Classroom;
import com.future.onlinetraining.repository.ClassroomRepository;
import com.future.onlinetraining.service.impl.ClassroomServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassroomServiceTest {

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @MockBean
    private ClassroomRepository classroomRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllPageableClassroom_Test() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Classroom classroom = Classroom.builder()
                .name("class1")
                .min_member(1)
                .max_member(5)
                .status("active")
                .module(null)
                .build();

        List<Classroom> classroomList = new ArrayList<>();
        classroomList.add(classroom);
        Page<Classroom> classroomPage = new PageImpl<>(classroomList, pageRequest, classroomList.size());

        when(classroomRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(classroomPage);

        Page<Classroom> results = classroomService.getAllPageableClassroom();

        System.out.println(results.getContent());
        Assert.assertEquals(classroomPage.getContent(), results.getContent());
        Mockito.verify(classroomRepository).findAll(Mockito.any(PageRequest.class));

    }

//    @After
//    public void tearDown(){
//        verifyNoMoreInteractions(classroomRepository);
//    }
}