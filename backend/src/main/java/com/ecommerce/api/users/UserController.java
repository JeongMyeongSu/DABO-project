package com.ecommerce.api.users;

import com.ecommerce.application.impl.DABOUserService;
import com.ecommerce.domain.dto.daboUserDto;
import com.ecommerce.domain.repository.entity.DABOUser;
import com.ecommerce.domain.exception.EmptyListException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    DABOUserService userService;

    @Autowired
    public UserController(DABOUserService userService) {
        Assert.notNull(userService, "userService 개체가 반드시 필요!");
        this.userService = userService;
    }
    @RequestMapping(value = "/me",method = RequestMethod.GET)
    public DABOUser myInfo(){
        DABOUser user = userService.get();
        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<DABOUser> list() {
        List<DABOUser> userList = userService.list();

        if (userList == null || userList.isEmpty() )
            throw new EmptyListException("NO DATA");

        return userList;
    }

    // 회원가입
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public DABOUser create(@RequestBody daboUserDto userDto) {
        System.out.println("userDto = " + userDto);
        DABOUser user = userService.add(userDto);
        return user;
    }
    // 닉네임 중복 검사
    @RequestMapping(value = "/checkNickname",method = RequestMethod.GET)
    public boolean checkNickname(@RequestParam("nickname") String nickname){
        return userService.DuplicatedNickname(nickname);
    }
    // 이메일 중복 검사
    @RequestMapping(value = "/checkEmail",method = RequestMethod.GET)
    public boolean checkEmail(@RequestParam("email") String email){
        return userService.DuplicatedEmail(email);
    }

    // 회원 정보 수정
    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public DABOUser update(@RequestBody daboUserDto userDto) {
        return userService.update(userDto);
    }

    // 회원 탈퇴 기능은 만들지 않음. 필요시 id말고 email받아서
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @RequestMapping(value = "/findPassword",method = RequestMethod.PATCH)
    public DABOUser resetPassword(@RequestBody String email) throws Exception{
        return userService.resetPassword(email);
    }
}
