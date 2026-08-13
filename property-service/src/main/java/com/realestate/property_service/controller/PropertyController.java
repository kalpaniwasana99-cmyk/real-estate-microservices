package com.realestate.property_service.controller;

import com.realestate.property_service.model.Property;
import com.realestate.property_service.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    // සියලුම ප්‍රොපර්ටිස් ලබා ගැනීමට (Get All Properties)
    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // ID එක මඟින් ප්‍රොපර්ටි එකක් ලබා ගැනීමට (Get Property by ID)
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable String id) {
        Optional<Property> property = propertyRepository.findById(id);
        return property.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // අලුත් ප්‍රොපර්ටි එකක් ඇතුළත් කිරීමට (Create Property)
    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyRepository.save(property);
    }

    // ප්‍රොපර්ටි එකක් මකා දැමීමට (Delete Property)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String id) {
        if (propertyRepository.existsById(id)) {
            propertyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}