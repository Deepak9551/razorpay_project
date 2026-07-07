package com.codingshuttle.razorpay.merchant.services.impl;

import com.codingshuttle.razorpay.common.enums.MerchantStatus;
import com.codingshuttle.razorpay.common.enums.UserRole;
import com.codingshuttle.razorpay.common.exceptions.DuplicateResourse;
import com.codingshuttle.razorpay.merchant.dto.MerchantRequest;
import com.codingshuttle.razorpay.merchant.dto.MerchantResponse;
import com.codingshuttle.razorpay.merchant.entity.AppUser;
import com.codingshuttle.razorpay.merchant.entity.Merchant;
import com.codingshuttle.razorpay.merchant.repositories.AppUserRepository;
import com.codingshuttle.razorpay.merchant.repositories.MerchantRepository;
import com.codingshuttle.razorpay.merchant.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final MerchantRepository merchantRepository;
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public MerchantResponse signUp(MerchantRequest request) {
        // when on board a merchant - app user is assigned to merchant to login

        // validate email is unique
        if(merchantRepository.existsByEmail(request.email())){
            throw new DuplicateResourse("DUPLICATE_EMAIL","Email already exists :"  + request.email());
        }
        Merchant merchant = Merchant.builder()
                .name(request.name())
                .email(request.email())
                .contact(request.contact())
                .address(request.address())
                .businessType(request.bussinessType())
                .businessName(request.bussinessName())
                .status(MerchantStatus.PENDING_KYC)
                .build();

       merchant =  merchantRepository.save(merchant); // operation 1 : save merchant

        AppUser appUser = AppUser.builder()
                .name(request.name())
                .email(request.email())
                .passwordHash(request.password()) // TODO: encrypt password using bcrypt
                .merchant(merchant)
                .role(UserRole.OWNER)
                .build();

        appUserRepository.save(appUser); // operation 2 : save app user

        // rollback if any operation fails
        // commit if all operations are successful
        return new MerchantResponse(merchant.getId(), merchant.getName(), merchant.getEmail(), merchant.getContact(), merchant.getAddress(), merchant.getBusinessType(), merchant.getBusinessName());
    }
}
