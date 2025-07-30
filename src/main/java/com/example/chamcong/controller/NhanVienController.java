package com.example.chamcong.controller;

import com.example.chamcong.entity.NhanVien;
import com.example.chamcong.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    // Lấy tất cả nhân viên
    @GetMapping
    public List<NhanVien> getAllNhanVien() {
        return nhanVienService.findAll();
    }

    // Lấy nhân viên theo ID
    @GetMapping("/{id}")
    public ResponseEntity<NhanVien> getNhanVienById(@PathVariable Long id) {
        NhanVien nv = nhanVienService.findById(id);
        if (nv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nv);
    }

    // Tạo nhân viên (không liên kết user - chỉ dùng nội bộ nếu cần)
    @PostMapping
    public ResponseEntity<NhanVien> createNhanVien(@RequestBody NhanVien nhanVien, Authentication auth) {
        try {
            nhanVien.setCreatedAt(LocalDateTime.now());
            if (auth != null && auth.isAuthenticated()) {
                nhanVien.setCreatedBy(auth.getName());
            }
            NhanVien saved = nhanVienService.save(nhanVien);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    // Tạo nhân viên và liên kết với user đang đăng nhập
    @PostMapping("/me")
    public ResponseEntity<NhanVien> createNhanVienForCurrentUser(@RequestBody NhanVien nv, Authentication authentication) {
        String username = authentication.getName();

        // Gán thông tin người tạo
        nv.setCreatedBy(username);
        nv.setCreatedAt(LocalDateTime.now());

        // Gán user hiện tại vào nhân viên
        nhanVienService.linkUserToNhanVien(nv, username);

        // Lưu lại
        NhanVien saved = nhanVienService.save(nv);
        return ResponseEntity.ok(saved);
    }


    // Lấy lịch sử tạo nhân viên (có thể filter theo createdBy nếu muốn)
    @GetMapping("/history")
    public ResponseEntity<List<NhanVien>> getAllNhanVienWithCreator() {
        List<NhanVien> list = nhanVienService.findAll();
        return ResponseEntity.ok(list);
    }

    // Cập nhật thông tin nhân viên
    @PutMapping("/{id}")
    public ResponseEntity<NhanVien> updateNhanVien(@PathVariable Long id, @RequestBody NhanVien nhanVien) {
        NhanVien existing = nhanVienService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        nhanVien.setId(id);
        NhanVien updated = nhanVienService.save(nhanVien);
        return ResponseEntity.ok(updated);
    }

    // Xóa nhân viên
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNhanVien(@PathVariable Long id) {
        NhanVien existing = nhanVienService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        nhanVienService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/link")
    public ResponseEntity<?> linkNhanVienToCurrentUser(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Chưa đăng nhập hoặc phiên đăng nhập không hợp lệ.");
        }

        String username = authentication.getName(); // sẽ không lỗi nữa

        NhanVien nv = nhanVienService.findById(id);
        if (nv == null) {
            return ResponseEntity.notFound().build();
        }

        if (nv.getUser() != null) {
            return ResponseEntity.badRequest().body("❌ Nhân viên này đã được gán với một tài khoản khác.");
        }

        boolean success = nhanVienService.linkUserToNhanVien(nv, username);
        if (!success) {
            return ResponseEntity.badRequest().body("❌ Không tìm thấy tài khoản người dùng.");
        }

        return ResponseEntity.ok("✅ Đã gán nhân viên ID " + id + " cho tài khoản " + username);
    }


    // Lấy nhân viên theo userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<NhanVien> getNhanVienByUserId(@PathVariable Long userId) {
        NhanVien nv = nhanVienService.findByUserId(userId);
        if (nv == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nv);
    }


}

