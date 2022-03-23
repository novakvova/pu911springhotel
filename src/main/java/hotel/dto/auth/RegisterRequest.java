package hotel.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegisterRequest {
    @NotNull @Email
    private String email;
    @NotNull
    private String password;
    private String fullName;
    private String reCaptchaToken;
}
