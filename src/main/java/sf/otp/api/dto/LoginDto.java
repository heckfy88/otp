package sf.otp.api.dto;

public record LoginDto(
        String email,
        String password
) {
}
