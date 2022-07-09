package com.example.nodayst.event.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.common.model.Score;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetEventScoreServiceTest {

    private GetEventScoreService getEventScoreService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        getEventScoreService = new GetEventScoreService(userRepository);
    }

    @Test
    public void getScoreFailNotFoundUserExceptionTest(){
        String userId = "userId";

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundUserException.class, ()->{
            getEventScoreService.getScore(userId);
        });
    }

    @Test
    public void getScoreSuccessTest(){
        String userId = "userId";
        User user = new User(userId, Score.ONE);

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        assertEquals(Score.ONE.getValue(),getEventScoreService.getScore(userId));
    }
}