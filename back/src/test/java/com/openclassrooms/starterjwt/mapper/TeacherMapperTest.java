package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;

public class TeacherMapperTest {
    
    @InjectMocks
    private TeacherMapper mapper = Mappers.getMapper(TeacherMapper.class);

    private Teacher mockTeacher = new Teacher();
    private Teacher mockTeacher2 = new Teacher();
    private TeacherDto mockTeacherDto = new TeacherDto();
    private TeacherDto mockTeacherDto2 = new TeacherDto();

    @BeforeEach
    void setUp() {
        mockTeacher.setId(1L);
        mockTeacher.setLastName("Doe");
        mockTeacher.setFirstName("John");

        mockTeacher2.setId(2L);
        mockTeacher2.setLastName("Smith");
        mockTeacher2.setFirstName("Will");

        mockTeacherDto.setId(1L);
        mockTeacherDto.setLastName("Doe");
        mockTeacherDto.setFirstName("John");

        mockTeacher2.setId(2L);
        mockTeacherDto2.setLastName("Smith");
        mockTeacherDto2.setLastName("Will");
    }

    @Test
    void testToEntity() {
        Teacher teacher = mapper.toEntity(mockTeacherDto);

        assertEquals(mockTeacherDto.getId(), teacher.getId());
        assertEquals(mockTeacherDto.getLastName(), teacher.getLastName());
        assertEquals(mockTeacherDto.getFirstName(), teacher.getFirstName());
    }

    @Test
    void testToDto() {
        TeacherDto teacherDto = mapper.toDto(mockTeacher);

        assertEquals(mockTeacher.getId(), teacherDto.getId());
        assertEquals(mockTeacher.getLastName(), teacherDto.getLastName());
        assertEquals(mockTeacher.getFirstName(), teacherDto.getFirstName());
    }

    @Test
    void testToEntityList() {
        List<TeacherDto> teacherDtoList = Arrays.asList(mockTeacherDto, mockTeacherDto2);

        List<Teacher> teacherList = mapper.toEntity(teacherDtoList);

        for(int i = 0; i < teacherDtoList.size(); i++) {
            assertEquals(teacherDtoList.get(i).getId(), teacherList.get(i).getId());
            assertEquals(teacherDtoList.get(i).getLastName(), teacherList.get(i).getLastName());
            assertEquals(teacherDtoList.get(i).getFirstName(), teacherList.get(i).getFirstName());
        }
    }

    @Test
    void testToDtoList() {
        List<Teacher> teacherList = Arrays.asList(mockTeacher, mockTeacher2);

        List<TeacherDto> teacherDtoList = mapper.toDto(teacherList);

        for(int i = 0; i < teacherList.size(); i++) {
            assertEquals(teacherList.get(i).getId(), teacherDtoList.get(i).getId()) ;
            assertEquals(teacherList.get(i).getLastName(), teacherDtoList.get(i).getLastName());
            assertEquals(teacherList.get(i).getFirstName(), teacherDtoList.get(i).getFirstName());
        }
    }
}
