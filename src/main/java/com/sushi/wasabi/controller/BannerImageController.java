package com.sushi.wasabi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushi.wasabi.dto.BannerImageDto;
import com.sushi.wasabi.dto.BannerImageRequestDto;
import com.sushi.wasabi.services.BannerImageService;
import com.sushi.wasabi.services.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Banner Image", description = "Operations for managing home page banner images (only by admin)")
public class BannerImageController {
    private final BannerImageService bannerImageService;
    private final ImageService imageService;

    @Operation(
            summary = "Get Banner Images",
            description = "Get active banner images to display in homepage"
    )
    @GetMapping("/banner-images")
    public List<BannerImageDto> getActiveBannerImages(){
        return bannerImageService.getActiveBannerImages();
    }

    @Operation(
            summary = "Post Banner Images",
            description = "Add new active banner images in homepage"
    )
    @PostMapping(value = "/admin/banner-images",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> addBannerImage(
            @RequestPart("data") String data,
            @RequestPart("image")MultipartFile image
            ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        BannerImageRequestDto description = mapper.readValue(data, BannerImageRequestDto.class);
        String imageUrl = imageService.uploadBannerImage(image);
        bannerImageService.addBannerImage(description, imageUrl);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Delete Banner Images",
            description = "Deactivate banner images from homepage"
    )
    @DeleteMapping(value = "/admin/banner-images/{id}")
    public ResponseEntity<Void> deleteBannerImage(
            @PathVariable Long id
    ) {
        bannerImageService.deactivateBannerImage(id);
        return ResponseEntity.ok().build();
    }
}
