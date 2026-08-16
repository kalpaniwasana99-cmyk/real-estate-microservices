package com.realestate.property_service.controller;

import com.realestate.property_service.model.Property;
import com.realestate.property_service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    private static final String UPLOAD_DIR = "uploads/";

    
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable String id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Property createProperty(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("location") String location,
            @RequestParam(value = "contactEmail", required = false) String contactEmail,
            @RequestParam(value = "contactPhone", required = false) String contactPhone,
            @RequestParam("ownerId") String ownerId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        String imageUrl = saveImage(image);
        Property property = new Property(null, title, description, price, location, ownerId, contactEmail, contactPhone, imageUrl);
        return propertyRepository.save(property);
    }

    
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Property> updateProperty(
            @PathVariable String id,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("location") String location,
            @RequestParam(value = "contactEmail", required = false) String contactEmail,
            @RequestParam(value = "contactPhone", required = false) String contactPhone,
            @RequestParam("ownerId") String ownerId,
            @RequestParam(value = "image", required = false) MultipartFile image) {

        Optional<Property> optionalProperty = propertyRepository.findById(id);

        if (optionalProperty.isPresent()) {
            Property existingProperty = optionalProperty.get();
            existingProperty.setTitle(title);
            existingProperty.setDescription(description);
            existingProperty.setPrice(price);
            existingProperty.setLocation(location);
            existingProperty.setContactEmail(contactEmail);
            existingProperty.setContactPhone(contactPhone);
            existingProperty.setOwnerId(ownerId);

            
            if (image != null && !image.isEmpty()) {
                String newImageUrl = saveImage(image);
                existingProperty.setImageUrl(newImageUrl);
            }

            Property updatedProperty = propertyRepository.save(existingProperty);
            return ResponseEntity.ok(updatedProperty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    
    @GetMapping("/my-properties/{ownerId}")
    public List<Property> getPropertiesByOwner(@PathVariable String ownerId) {
        return propertyRepository.findByOwnerId(ownerId);
    }

     
    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path file = Paths.get(UPLOAD_DIR).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.IMAGE_JPEG) 
                        .body(resource);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }


    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) return null;
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // uploads folder එක නැත්නම් හදනවා
            }
            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(image.getInputStream(), filePath);
            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}