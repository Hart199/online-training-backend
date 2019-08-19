package com.future.onlinetraining.service.impl;

import com.future.onlinetraining.dto.RatingDTO;
import com.future.onlinetraining.entity.Module;
import com.future.onlinetraining.entity.ModuleRating;
import com.future.onlinetraining.entity.User;
import com.future.onlinetraining.repository.ModuleRatingRepository;
import com.future.onlinetraining.repository.ModuleRepository;
import com.future.onlinetraining.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModuleServiceImplTest {

    @InjectMocks
    ModuleServiceImpl moduleService;

    @MockBean
    ModuleRatingRepository moduleRatingRepository;
    @MockBean
    UserService userService;
    @MockBean
    ModuleRepository moduleRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getRatings_Test() {
        when(moduleRatingRepository
                .findAllByModuleId(Mockito.anyInt(), Mockito.any(PageRequest.class))).thenReturn(null);
        Assert.assertEquals(moduleService.getRatings(1, Mockito.mock(PageRequest.class)), null);
    }

    @Test(expected = RuntimeException.class)
    public void addRating_WhenModuleNotPresent_Test() {
        when(userService.getUserFromSession()).thenReturn(Mockito.mock(User.class));
        when(moduleRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        moduleService.addRating(1, Mockito.mock(RatingDTO.class));
    }

    @Test
    public void addRating_Test() {
        when(userService.getUserFromSession()).thenReturn(Mockito.mock(User.class));
        when(moduleRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(Mockito.mock(Module.class)));
        when(moduleRatingRepository
                .findByModuleIdAndUserId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(Optional.of(Mockito.mock(ModuleRating.class)));
        when(moduleRatingRepository.save(Mockito.any(ModuleRating.class)))
                .thenReturn(null);
        Assert.assertEquals(
                moduleService.addRating(1, Mockito.mock(RatingDTO.class)), null);
    }
}