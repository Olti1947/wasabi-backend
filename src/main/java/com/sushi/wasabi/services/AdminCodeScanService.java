package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.dto.ExpenseRequestDto;
import com.sushi.wasabi.dto.UserInfoAdminDto;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCodeScanService {
    private final UserRepository userRepository;
    private final DiscountService discountService;

    @Transactional
    public UserInfoAdminDto getUserInfoByCode(String qrCode){
        User user = userRepository.findByQrCodeToken(qrCode)
                .orElseThrow(()->new RuntimeException("No such user with this token"));

        List<DiscountDto> discounts = discountService.getUserActiveDiscounts(user.getId());

        UserInfoAdminDto userInfoAdminDto = new UserInfoAdminDto();

        userInfoAdminDto.setId(user.getId());
        userInfoAdminDto.setEmail(user.getEmail());
        userInfoAdminDto.setFirstName(user.getFirstName());
        userInfoAdminDto.setLastName(user.getLastName());
        userInfoAdminDto.setSpending(user.getSpending());
        userInfoAdminDto.setDiscounts(discounts);

        return userInfoAdminDto;
    }

    public void addSpendingAmount(ExpenseRequestDto expenseRequestDto){
        User user = userRepository.findById(expenseRequestDto.getUserId())
                .orElseThrow(()-> new RuntimeException("No such user"));
        user.setSpending(user.getSpending().add(expenseRequestDto.getAmount()));
        userRepository.save(user);
    }

    public BigDecimal getSpendingAmount(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No such user"));

        return user.getSpending();
    }

}
