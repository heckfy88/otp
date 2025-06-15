package sf.otp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sf.otp.api.dto.UserDto;
import sf.otp.dao.domain.User;
import sf.otp.dao.repository.UserRepository;
import sf.otp.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto register(UserDto user) {
        User savedUser = userRepository.create(new User(user));

        return new UserDto(savedUser);
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.getAll();

        return users.stream()
                .map(UserDto::new)
                .toList();
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
