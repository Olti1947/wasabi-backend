package com.sushi.wasabi.services;

import com.sushi.wasabi.dto.BannerImageDto;
import com.sushi.wasabi.dto.BannerImageRequestDto;
import com.sushi.wasabi.entity.BannerImage;
import com.sushi.wasabi.repository.BannerImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerImageService {
private final BannerImageRepository bannerImageRepository;

public BannerImageDto toDto(BannerImage bannerImage){
    BannerImageDto dto = new BannerImageDto();
    dto.setId(bannerImage.getId());
    dto.setDescription(bannerImage.getDescription());
    dto.setImageUrl(bannerImage.getImageUrl());

    return dto;
}

public List<BannerImageDto> getActiveBannerImages(){
    List<BannerImage> bannerImages = bannerImageRepository.findAllByActiveTrue();
    if(bannerImages == null) return null;
 return bannerImages.stream().map(this::toDto).toList();
}

public void addBannerImage(BannerImageRequestDto bannerImageDto, String imageUrl){
    BannerImage bannerImage = new BannerImage();

    bannerImage.setDescription(bannerImageDto.getDescription());
    bannerImage.setImageUrl(imageUrl);
    bannerImage.setActive(true);

    bannerImageRepository.save(bannerImage);
}

public void deactivateBannerImage(Long bannerId) {
    BannerImage bannerImage = bannerImageRepository.findById(bannerId).orElseThrow(() -> new RuntimeException("No such banner"));
    bannerImage.setActive(false);
    bannerImageRepository.save(bannerImage);
}
}
