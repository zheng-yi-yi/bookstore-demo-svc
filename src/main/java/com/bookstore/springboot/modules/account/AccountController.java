package com.bookstore.springboot.modules.account;

import com.bookstore.springboot.modules.account.dto.*;
import com.bookstore.springboot.modules.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterDto input) {
        return accountService.register(input);
    }

    @PostMapping("/login")
    public LoginResultDto login(@RequestBody LoginDto input) {
        return accountService.login(input);
    }

    @PostMapping("/send-password-reset-code")
    public void sendPasswordResetCode(@RequestBody SendPasswordResetCodeDto input) {
        accountService.sendPasswordResetCode(input);
    }

    @PostMapping("/verify-password-reset-token")
    public boolean verifyPasswordResetToken(@RequestBody VerifyPasswordResetTokenDto input) {
        return accountService.verifyPasswordResetToken(input);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody ResetPasswordDto input) {
        accountService.resetPassword(input);
    }
}
