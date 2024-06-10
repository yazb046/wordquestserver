package com.wordquest.server.imaginary;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public String save(Long userId, Long themeId, MultipartFile file) throws IOException {
        Image image =
                imageRepository.insert(
                        Image.builder()
                                .userId(userId)
                                .themeId(themeId)
                                .image(new Binary(BsonBinarySubType.BINARY, file.getBytes()))
                                .build()
                );
        return image.getId();
    }

    public Optional<Image> getBy(String id) {
        return imageRepository.findById(id);
    }
}