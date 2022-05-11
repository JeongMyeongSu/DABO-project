package com.ecommerce.application.impl;

import com.ecommerce.config.auth.SsafyUserDetails;
import com.ecommerce.domain.repository.entity.DABOUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {

    public DABOUser getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (principal instanceof SsafyUserDetails)
            return ((SsafyUserDetails)principal).getUser();
        else
            return new DABOUser();
    }
}
