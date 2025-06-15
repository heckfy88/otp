package sf.otp.service;

import sf.otp.api.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserDto register(UserDto user);
    List<UserDto> getAll();

    void deleteUser(UUID id);

}
