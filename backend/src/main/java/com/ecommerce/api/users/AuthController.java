package com.ecommerce.api.users;

import java.util.NoSuchElementException;

import com.ecommerce.api.wallet.EthereumController;
import com.ecommerce.application.impl.DABOUserService;
import com.ecommerce.domain.dto.loginDto;
import com.ecommerce.domain.repository.entity.DABOUser;
import com.ecommerce.domain.repository.response.UserLoginPostRes;
import com.ecommerce.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * 인증 관련 API 요청 처리를 위한 컨트롤러 정의.
 *
 * @author mo_ah
 * 	: update url(l : 34)
 * 	: autowired filed injection -> constructor injection(l : 43)
 */

@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/api/user")
public class AuthController {
//	@Autowired
//	UserService userService;
	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	public static final Logger log = LoggerFactory.getLogger(EthereumController.class);

	private final DABOUserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public AuthController(DABOUserService userService,BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@PostMapping("/login")
	@ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "유효하지 않은 아이디/패스워드"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserLoginPostRes> login(@RequestBody loginDto loginInfo) {
		log.debug("Login Start");
		String email = loginInfo.getEmail();
		String password = loginInfo.getPassword();
		DABOUser user ;
		try {
			user = userService.getUserByEmail(email);
		} catch (NoSuchElementException e) {
			return ResponseEntity.status(404).body(UserLoginPostRes.of(404, "user doesn't exist", null));
		}
		
		// 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
		if(passwordEncoder.matches(password, user.getPassword())) {
			// 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
			return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(email)));
		}
		// 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
		return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
	}
}
