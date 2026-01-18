package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.DiscountDto;
import com.sushi.wasabi.entity.Discount;
import com.sushi.wasabi.entity.User;
import com.sushi.wasabi.entity.UserDiscount;
import com.sushi.wasabi.enums.UserDiscountStatus;
import com.sushi.wasabi.repository.DiscountRepository;
import com.sushi.wasabi.repository.UserDiscountRepository;
import com.sushi.wasabi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiscountService {
private final DiscountRepository discountRepository;
private final UserDiscountRepository userDiscountRepository;
private final UserRepository userRepository;

public List<DiscountDto> getAvailableDiscounts(Integer userId){
    LocalDateTime now = LocalDateTime.now();

    return discountRepository.findAllActive(now)
            .stream()
            .filter(d -> !userDiscountRepository.existsByUser_IdAndDiscount_Id(userId, d.getId()))
            .map(this::toDto)
            .collect(Collectors.toList());
}

public void activateDiscount(Integer userId, Long discountId){
    Discount discount = discountRepository.findById(discountId)
            .orElseThrow(()->new RuntimeException("Discount not found"));

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    UserDiscount userDiscount = new UserDiscount();
    userDiscount.setUser(user);
    userDiscount.setDiscount(discount);
    userDiscount.setStatus(UserDiscountStatus.ACTIVE);
    userDiscount.setActivatedAt(LocalDateTime.now());
    userDiscount.setExpiresAt(discount.getEndsAt());

    userDiscountRepository.save(userDiscount);
}

    public List<DiscountDto> getUserActiveDiscounts(Integer userId) {
        LocalDateTime now = LocalDateTime.now();

        return userDiscountRepository.findByUser_IdAndStatusAndExpiresAtAfter(userId, UserDiscountStatus.ACTIVE, now)
                .stream()
                .map(ud -> toDto(ud.getDiscount()))
                .collect(Collectors.toList());
    }

    private DiscountDto toDto(Discount discount) {
        return new DiscountDto(
                discount.getId(),
                discount.getTitle(),
                discount.getDescription(),
                discount.getType().name(),
                discount.getImageUrl(),
                discount.getValue(),
                discount.getStartsAt(),
                discount.getEndsAt(),
                discount.getMinOrderValue(),
                discount.isStackable()
        );
}
}
