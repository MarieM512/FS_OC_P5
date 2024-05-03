package com.openclassrooms.starterjwt.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.openclassrooms.starterjwt.controllers.TeacherController;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;

@SpringBootTest
public class TeacherControllerIntegrationTest {
    
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherController teacherController;

    @AfterEach
    void cleanUp() {
        teacherRepository.deleteAll();
    }

    @Test
    @DirtiesContext
    void testFindTeacherByIdSuccessfull() {
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);
        mockTeacher.setFirstName("John");
        mockTeacher.setLastName("Doe");

        teacherRepository.save(mockTeacher);

        ResponseEntity<?> response = teacherController.findById(String.valueOf(mockTeacher.getId()));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(TeacherDto.class, response.getBody());

        TeacherDto teacher = (TeacherDto) response.getBody();
        assertNotNull(teacher);
        assertEquals(mockTeacher.getId(), teacher.getId());
        assertEquals(mockTeacher.getFirstName(), teacher.getFirstName());
        assertEquals(mockTeacher.getLastName(), teacher.getLastName());
    }

    @Test
    @DirtiesContext
    void testFindTeacherByIdNotFound() {
        ResponseEntity<?> response = teacherController.findById("1");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testFindTeacherByIdBadRequest() {
        ResponseEntity<?> response = teacherController.findById("notId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DirtiesContext
    void testFindAllTeacher() {
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);
        mockTeacher.setFirstName("John");
        mockTeacher.setLastName("Doe");
        List<Teacher> mockTeacherList = new ArrayList<Teacher>();
        mockTeacherList.add(mockTeacher);

        teacherRepository.save(mockTeacher);

        ResponseEntity<?> response = teacherController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(ArrayList.class, response.getBody());

        List<TeacherDto> teacherDtoList = (List<TeacherDto>) response.getBody();
        assertThat(teacherDtoList).isNotEmpty();
        assertEquals(mockTeacherList.size(), teacherDtoList.size());
        assertEquals(mockTeacher.getId(), teacherDtoList.get(0).getId());
        assertEquals(mockTeacher.getFirstName(), teacherDtoList.get(0).getFirstName());
        assertEquals(mockTeacher.getLastName(), teacherDtoList.get(0).getLastName());
    }
}
