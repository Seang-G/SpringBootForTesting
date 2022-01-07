package com.coco.board.web.controller;

import com.coco.board.config.auth.LoginUser;
import com.coco.board.validator.EmailCheckValidator;
import com.coco.board.validator.NicknameCheckValidator;
import com.coco.board.validator.UsernameCheckValidator;
import com.coco.board.service.UserService;
import com.coco.board.web.dto.user.UserRequestDto;
import com.coco.board.web.dto.user.UserSessionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * 회원 관련 Controller
 */
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final UsernameCheckValidator usernameCheckValidator;
    private final NicknameCheckValidator nicknameCheckValidator;
    private final EmailCheckValidator emailCheckValidator;

    /* 커스텀 유효성 검증을 위해 추가 */
    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        binder.addValidators(usernameCheckValidator);
        binder.addValidators(nicknameCheckValidator);
        binder.addValidators(emailCheckValidator);
    }

    @GetMapping("/auth/join")
    public String join() {
        return "/user/user-join";
    }

    /* 회원가입 */
    @PostMapping("/auth/joinProc")
    public String joinProc(@Valid UserRequestDto userDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
             /* 회원가입 실패시 입력 데이터 값을 유지 */
            model.addAttribute("userDto", userDto);

            /* 유효성 통과 못한 필드와 메시지를 핸들링 */
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key, validatorResult.get(key));
            }
            /* 회원가입 페이지로 다시 리턴 */
            return "/user/user-join";
        }
        userService.userJoin(userDto);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String login(@RequestParam(value = "error", required = false)String error,
                        @RequestParam(value = "exception", required = false)String exception,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/user/user-login";
    }

    /* Security에서 로그아웃은 기본적으로 POST지만, GET으로 우회 */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    /* 회원정보 수정 */
    @GetMapping("/modify")
    public String modify(@LoginUser UserSessionDto user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "/user/user-modify";
    }
}
