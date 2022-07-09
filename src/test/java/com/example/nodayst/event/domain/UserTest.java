package com.example.nodayst.event.domain;

import static org.junit.jupiter.api.Assertions.*;

import com.example.nodayst.common.model.Score;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    public void UserAddScoreTest(){
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, Score.ONE);

        user.addScore(Score.ONE);

        assertEquals(new Score(2),user.getScore());
    }
}