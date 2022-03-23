package hotel.controllers.api;

import hotel.configuration.security.JwtTokenUtil;
import hotel.configuration.security.captcha.CaptchaSettings;
import hotel.configuration.security.captcha.GoogleResponse;
import hotel.constants.Roles;
import hotel.dto.auth.AuthRequest;
import hotel.dto.auth.RegisterRequest;
import hotel.dto.auth.UserView;
import hotel.entities.UserEntity;
import hotel.mapper.UserMapper;
import hotel.repositories.RoleRepository;
import hotel.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping(path = "api/public")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    private final CaptchaSettings captchaSettings;
    @Autowired
    private final RestOperations restTemplate;
    protected static final String RECAPTCHA_URL_TEMPLATE = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @PostMapping("login")
    public ResponseEntity<UserView> login(@RequestBody @Valid AuthRequest request) {
        try {
            UserView userView = loginUser(request.getUsername(), request.getPassword());
            return ResponseEntity.ok()
                   .body(userView);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        try {
            String url = String.format(RECAPTCHA_URL_TEMPLATE, captchaSettings.getSicretkey(), request.getReCaptchaToken());
            try {
                final GoogleResponse googleResponse = restTemplate.getForObject(url, GoogleResponse.class);
                if (!googleResponse.isSuccess()) {
                    throw new Exception("reCaptcha was not successfully validated");
                }
            }
            catch (Exception rce) {
                String str = rce.getMessage();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            UserEntity userdb =  userRepository.findByUsername(request.getEmail());
            if(userdb!=null)
            {
                return ResponseEntity.badRequest()
                        .body("Дана пошта уже зареєстрована");
            }
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            userdb = new UserEntity();
            userdb.setUsername(request.getEmail());
            userdb.setFullName(request.getFullName());
            userdb.setPassword(encoder.encode(request.getPassword()));
            userdb.setRoles(Arrays.asList(
                    roleRepository.findByName(Roles.User)));
            this.userRepository.save(userdb);

            UserView userView = loginUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok()
                    .body(userView);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private UserView loginUser(String username, String password) throws BadCredentialsException {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        username,
                        password));

        User user = (User) authenticate.getPrincipal();
        UserEntity dbUser = userRepository
                .findByUsername(user.getUsername());
        UserView userView = userMapper.UserToUserView(dbUser);// new UserView();
        //userView.setUsername(user.getUsername());
        userView.setToken(jwtTokenUtil.generateAccessToken(dbUser));
        return userView;
    }
}
