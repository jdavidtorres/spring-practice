package co.com.jdti.springsecurity.auth;

import co.com.jdti.springsecurity.config.JwtService;
import co.com.jdti.springsecurity.user.Role;
import co.com.jdti.springsecurity.user.User;
import co.com.jdti.springsecurity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		User user = User.builder().firstName(request.getFirstname()).lastname(request.getLastname())
			.email(request.getEmail())
			.password(passwordEncoder.encode(request.getPassword()))
			.role(Role.USER).build();
		userRepository.save(user);
		String jwtToken = jwtService.generateToken(new HashMap<>(), user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	public AuthenticationResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
		String jwtToken = jwtService.generateToken(new HashMap<>(), user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}
}
