package com.example.lesure.user;

import com.example.lesure.user.model.User;
import com.example.lesure.user.model.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileDto getUserById(Long id) {
        return userRepository.findById(id).map(this::toUserProfileDto).orElse(null);
    }

    public UserProfileDto toUserProfileDto(User user) {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setFirstName(user.getFirstName());
        userProfileDto.setLastName(user.getLastName());
        userProfileDto.setEmail(user.getEmail());
        return userProfileDto;
    }
}
