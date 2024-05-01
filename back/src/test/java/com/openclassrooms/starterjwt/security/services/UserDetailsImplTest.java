package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.GrantedAuthority;

public class UserDetailsImplTest {

    @InjectMocks
    private UserDetailsImpl mockUserDetails;

    @BeforeEach
    public void setup() {
        mockUserDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("Johnny")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = mockUserDetails.getAuthorities();
        assertEquals(new HashSet<GrantedAuthority>(), authorities);
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(mockUserDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(mockUserDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(mockUserDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(mockUserDetails.isEnabled());
    }

    @Test
    void testEquals() {
        UserDetailsImpl userId1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl userId2 = UserDetailsImpl.builder().id(2L).build();

        assertEquals(mockUserDetails, userId1);
        assertNotEquals(mockUserDetails, userId2);
    }

}
