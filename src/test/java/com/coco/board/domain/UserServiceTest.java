package com.coco.board.domain;


import com.coco.board.application.dto.UserDto;
import com.coco.board.infrastructure.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Test
    public void userJoin_WhenJoin_ShouldSaveUser() {
        // Given
        UserDto userDto = new UserDto();

    }

}
