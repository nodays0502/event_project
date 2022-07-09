package com.example.nodayst.event.application;

import com.example.nodayst.common.exception.user.NotFoundUserException;
import com.example.nodayst.event.domain.User;
import com.example.nodayst.event.infra.UserRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetEventScoreService {

    private final UserRepository userRepository;

    public long getScore(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundUserException();
        });
        return user.getScore().getValue();
    }
}
