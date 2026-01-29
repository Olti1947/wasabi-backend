package com.sushi.wasabi.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageService {
private final Cloudinary cloudinary;

public String uploadFoodImage(MultipartFile file) {
    if(file.isEmpty())
        throw new IllegalArgumentException("Image is required");
    if(!file.getContentType().startsWith("image/"))
        throw new IllegalArgumentException("Only image files allowed");
    if(file.getSize() > 5_000_000)
        throw new IllegalArgumentException("Max image size is 5MB");

    try {
        Map<?, ?> result = cloudinary.uploader().upload(
        file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "food-items",
                        "resource_type", "image"
                )
                );
        return result.get("secure_url").toString();
    } catch (IOException e) {
        throw new RuntimeException("Image upload failed", e);
    }
}

    public String uploadDiscountImage(MultipartFile file) {
        if(file.isEmpty())
            throw new IllegalArgumentException("Image is required");
        if(!file.getContentType().startsWith("image/"))
            throw new IllegalArgumentException("Only image files allowed");
        if(file.getSize() > 5_000_000)
            throw new IllegalArgumentException("Max image size is 5MB");

        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "discount-items",
                            "resource_type", "image"
                    )
            );
            return result.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }

}
