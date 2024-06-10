package com.wordquest.server.imaginary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

//    @PostMapping("/{userId}/{themeId}")
//    public CardDTO createCard(
//            @PathVariable Long themeId,
//            @PathVariable Long userId,
////            @RequestBody CardDTO card
//    ){
//        return imageService.save(themeId, card);
//    }

//    @GetMapping("/{userId}/{themeId}")
//    public Page<String> getImagesUrls(
//            @PathVariable Long userId,
//            @PathVariable Long themeId,
//            @RequestParam int pageNo,
//            @RequestParam int pageSize
//    ){
//        return imageService.getAllUrlsBy(themeId, Helper.buildPageable(pageNo, pageSize, "asc", "id"));
//    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(
            @PathVariable String id
    ) {
        Optional<Image> imageOptional = imageService.getBy(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust based on your image type
            return new ResponseEntity<>(image.getImage().getData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        return Base64.getEncoder().encodeToString(
//                imageService.getBy(id).getImage().getData()
//        );
    }

    @PostMapping("/upload/{themeId}")
    public ResponseEntity<String> uploadPhoto(
            @PathVariable Long themeId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            return ResponseEntity.ok(
                    imageService.save(0L, themeId, file)
            );
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
    }
}