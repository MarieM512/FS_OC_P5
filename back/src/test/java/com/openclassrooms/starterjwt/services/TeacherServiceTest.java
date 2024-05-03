// package com.openclassrooms.starterjwt.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import com.openclassrooms.starterjwt.models.Session;
// import com.openclassrooms.starterjwt.models.Teacher;
// import com.openclassrooms.starterjwt.repository.TeacherRepository;

// public class TeacherServiceTest {

//     @InjectMocks
//     private TeacherService service;

//     @Mock
//     private TeacherRepository repository;

//     private Teacher mockTeacher = new Teacher();

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void testFindAll() {
//         List<Teacher> teachers = new ArrayList<Teacher>();
//         when(repository.findAll()).thenReturn(teachers);

//         List<Teacher> sessionResult = service.findAll();

//         verify(repository).findAll();
//         assertEquals(teachers, sessionResult);
//     }

//     @Test
//     void testFindById() {
//         when(repository.findById(1L)).thenReturn(Optional.of(mockTeacher));

//         Teacher teacher = service.findById(1L);

//         verify(repository).findById(1L);
//         assertEquals(mockTeacher, teacher);
//     }
// }
