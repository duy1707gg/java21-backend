package com.example.chamcong.controller;

import com.example.chamcong.entity.CongViec;
import com.example.chamcong.service.CongViecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/congviec")
public class CongViecController {
    @Autowired
    private CongViecService congViecService;

    @GetMapping
    public List<CongViec> getAllCongViec() {
        return congViecService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongViec> getCongViecById(@PathVariable Long id) {
        CongViec cv = congViecService.findById(id);
        if (cv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cv);
    }

    @PostMapping
    public CongViec createCongViec(@RequestBody CongViec congViec) {
        return congViecService.save(congViec);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CongViec> updateCongViec(@PathVariable Long id, @RequestBody CongViec congViec) {
        CongViec existing = congViecService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        congViec.setId(id);
        CongViec updated = congViecService.save(congViec);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCongViec(@PathVariable Long id) {
        CongViec existing = congViecService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        congViecService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
