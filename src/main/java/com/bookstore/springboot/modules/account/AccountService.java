package com.bookstore.springboot.modules.account;

import com.bookstore.springboot.modules.account.dto.*;
import com.bookstore.springboot.modules.user.dto.UserDto;

public interface AccountService {
    UserDto register(RegisterDto input);
    void sendPasswordResetCode(SendPasswordResetCodeDto input);
    boolean verifyPasswordResetToken(VerifyPasswordResetTokenDto input);
    void resetPassword(ResetPasswordDto input);
}
