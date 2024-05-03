package com.openclassrooms.starterjwt.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;

public class TeacherControllerTest {
    
    @InjectMocks
    private TeacherController controller;

    @Mock
    private TeacherService service;

    @Mock
    private TeacherMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindTeacherByIdSuccessfull() {
        Teacher teacher = new Teacher();

        when(service.findById(1L)).thenReturn(teacher);

        ResponseEntity<?> response = controller.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).findById(1L);
        verify(mapper).toDto(teacher);
    }

    @Test
    void testFindTeacherByIdNotFound() {
        when(service.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = controller.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(service).findById(1L);
    }

    @Test
    void testFindTeacherByIdBadRequest() {
        ResponseEntity<?> response = controller.findById("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testFindAllTeacher() {
        List<Teacher> teachers = new ArrayList<Teacher>();
        when(service.findAll()).thenReturn(teachers);

        ResponseEntity<?> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(service).findAll();
        verify(mapper).toDto(teachers);
    }
}
