package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;

public class SessionMapperTest {
    
    @InjectMocks
    private SessionMapper mapper = Mappers.getMapper(SessionMapper.class);

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    private Session mockSession = new Session();
    private Session mockSession2 = new Session();
    private Teacher mockTeacher = new Teacher();
    private Teacher mockTeacher2 = new Teacher();
    private SessionDto mockSessionDto = new SessionDto();
    private SessionDto mockSessionDto2 = new SessionDto();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockTeacher.setId(1L);
        mockTeacher2.setId(2L);

        mockSession.setName("Session");
        mockSession.setDate(new Date());
        mockSession.setTeacher(mockTeacher);
        mockSession.setDescription("Description");

        mockSession2.setName("Session 2");
        mockSession2.setDate(new Date());
        mockSession2.setTeacher(mockTeacher2);
        mockSession2.setDescription("Description 2");

        mockSessionDto.setName("Session");
        mockSessionDto.setDate(new Date());
        mockSessionDto.setTeacher_id(mockTeacher.getId());
        mockSessionDto.setDescription("Description");

        mockSessionDto2.setName("Session 2");
        mockSessionDto2.setDate(new Date());
        mockSessionDto2.setTeacher_id(mockTeacher2.getId());
        mockSessionDto2.setDescription("Description 2");
    }

    @Test
    void testToEntity() {
        when(teacherService.findById(mockSessionDto.getTeacher_id())).thenReturn(mockTeacher);

        Session session = mapper.toEntity(mockSessionDto);

        assertEquals(mockSessionDto.getName(), session.getName());
        assertEquals(mockSessionDto.getDate(), session.getDate());
        assertEquals(mockSessionDto.getTeacher_id(), session.getTeacher().getId());
        assertEquals(mockSessionDto.getDescription(), session.getDescription());
    }

    @Test
    void testToDto() {
        SessionDto sessionDto = mapper.toDto(mockSession);

        assertEquals(mockSession.getName(), sessionDto.getName());
        assertEquals(mockSession.getDate(), sessionDto.getDate());
        assertEquals(mockSession.getTeacher().getId(), sessionDto.getTeacher_id());
        assertEquals(mockSession.getDescription(), sessionDto.getDescription());
    }

    @Test
    void testToEntityList() {
        List<SessionDto> sessionDtoList = Arrays.asList(mockSessionDto, mockSessionDto2);

        when(teacherService.findById(mockSessionDto.getTeacher_id())).thenReturn(mockTeacher);
        when(teacherService.findById(mockSessionDto2.getTeacher_id())).thenReturn(mockTeacher2);

        List<Session> sessionList = mapper.toEntity(sessionDtoList);

        for(int i = 0; i < sessionDtoList.size(); i++) {
            assertEquals(sessionDtoList.get(i).getName(), sessionList.get(i).getName());
            assertEquals(sessionDtoList.get(i).getDate(), sessionList.get(i).getDate());
            assertEquals(sessionDtoList.get(i).getTeacher_id(), sessionList.get(i).getTeacher().getId());
            assertEquals(sessionDtoList.get(i).getDescription(), sessionList.get(i).getDescription());
        }
    }

    @Test
    void testToDtoList() {
        List<Session> sessionList = Arrays.asList(mockSession, mockSession2);

        List<SessionDto> sessionDtoList = mapper.toDto(sessionList);

        for(int i = 0; i < sessionList.size(); i++) {
            assertEquals(sessionList.get(i).getName(), sessionDtoList.get(i).getName()) ;
            assertEquals(sessionList.get(i).getDate(), sessionDtoList.get(i).getDate());
            assertEquals(sessionList.get(i).getTeacher().getId(), sessionDtoList.get(i).getTeacher_id());
            assertEquals(sessionList.get(i).getDescription(), sessionDtoList.get(i).getDescription());
        }
    }
}
