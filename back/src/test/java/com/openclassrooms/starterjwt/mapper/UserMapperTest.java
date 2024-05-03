package com.openclassrooms.starterjwt.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;

public class UserMapperTest {
    
    @InjectMocks
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    private User mockUser = new User();
    private User mockUser2 = new User();
    private UserDto mockUserDto = new UserDto();
    private UserDto mockUserDto2 = new UserDto();

    @BeforeEach
    void setUp() {
        mockUser.setId(1L);
        mockUser.setEmail("test@oc.com");
        mockUser.setLastName("Doe");
        mockUser.setFirstName("John");
        mockUser.setPassword("password");

        mockUser2.setId(2L);
        mockUser2.setEmail("test@oc2.com");
        mockUser2.setLastName("Smith");
        mockUser2.setFirstName("Will");
        mockUser2.setPassword("password2");

        mockUserDto.setId(1L);
        mockUserDto.setEmail("test@oc.com");
        mockUserDto.setLastName("Doe");
        mockUserDto.setFirstName("John");
        mockUserDto.setPassword("password");

        mockUserDto2.setId(2L);
        mockUserDto2.setEmail("test@oc2.com");
        mockUserDto2.setLastName("Smith");
        mockUserDto2.setFirstName("Will");
        mockUserDto2.setPassword("password2");
    }

    @Test
    void testToEntity() {
        User User = mapper.toEntity(mockUserDto);

        assertEquals(mockUserDto.getId(), User.getId());
        assertEquals(mockUserDto.getEmail(), User.getEmail());
        assertEquals(mockUserDto.getLastName(), User.getLastName());
        assertEquals(mockUserDto.getFirstName(), User.getFirstName());
        assertEquals(mockUserDto.getPassword(), User.getPassword());
    }

    @Test
    void testToDto() {
        UserDto userDto = mapper.toDto(mockUser);

        assertEquals(mockUser.getId(), userDto.getId());
        assertEquals(mockUser.getEmail(), userDto.getEmail());
        assertEquals(mockUser.getLastName(), userDto.getLastName());
        assertEquals(mockUser.getFirstName(), userDto.getFirstName());
        assertEquals(mockUser.getPassword(), userDto.getPassword());
    }

    @Test
    void testToEntityList() {
        List<UserDto> userDtoList = Arrays.asList(mockUserDto, mockUserDto2);

        List<User> userList = mapper.toEntity(userDtoList);

        for(int i = 0; i < userDtoList.size(); i++) {
            assertEquals(userDtoList.get(i).getId(), userList.get(i).getId());
            assertEquals(userDtoList.get(i).getEmail(), userList.get(i).getEmail());
            assertEquals(userDtoList.get(i).getLastName(), userList.get(i).getLastName());
            assertEquals(userDtoList.get(i).getFirstName(), userList.get(i).getFirstName());
            assertEquals(userDtoList.get(i).getPassword(), userList.get(i).getPassword());
        }
    }

    @Test
    void testToDtoList() {
        List<User> userList = Arrays.asList(mockUser, mockUser2);

        List<UserDto> userDtoList = mapper.toDto(userList);

        for(int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getId(), userDtoList.get(i).getId());
            assertEquals(userList.get(i).getEmail(), userDtoList.get(i).getEmail());
            assertEquals(userList.get(i).getLastName(), userDtoList.get(i).getLastName());
            assertEquals(userList.get(i).getFirstName(), userDtoList.get(i).getFirstName());
            assertEquals(userList.get(i).getPassword(), userDtoList.get(i).getPassword());
        }
    }
}
