package com.example.chamcong.controller;

import com.example.chamcong.entity.ChamCong;
import com.example.chamcong.entity.CongViec;
import com.example.chamcong.entity.NhanVien;
import com.example.chamcong.entity.User;
import com.example.chamcong.security.CustomUserDetails;
import com.example.chamcong.service.ChamCongService;
import com.example.chamcong.service.CongViecService;
import com.example.chamcong.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chamcong")
public class ChamCongController {

    @Autowired
    private ChamCongService chamCongService;

    @Autowired
    private NhanVienService nhanVienService;

    @Autowired
    private CongViecService congViecService;

    @GetMapping
    public List<ChamCong> getAllChamCong() {
        return chamCongService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamCong> getChamCongById(@PathVariable Long id) {
        ChamCong cc = chamCongService.findById(id);
        if (cc == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cc);
    }

    @PostMapping("/nhanvien/{nhanVienId}")
    public ResponseEntity<?> createChamCongForNhanVien(
            @PathVariable Long nhanVienId,
            @RequestBody ChamCong chamCong) {
        try {
            NhanVien nhanVien = nhanVienService.findById(nhanVienId);
            if (nhanVien == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy nhân viên với ID: " + nhanVienId);
            }

            // Kiểm tra công việc
            if (chamCong.getCongViec() == null || chamCong.getCongViec().getId() == null) {
                return ResponseEntity.badRequest().body("Thiếu thông tin công việc.");
            }

            CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
            if (congViec == null) {
                return ResponseEntity.badRequest().body("Công việc không tồn tại.");
            }

            chamCong.setCongViec(congViec);
            chamCong.setNhanVien(nhanVien);

            ChamCong saved = chamCongService.save(chamCong);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi tạo chấm công: " + e.getMessage());
        }
    }


    @PostMapping("/ca-nhan")
    public ResponseEntity<?> createChamCongCaNhan(@RequestBody ChamCong chamCong, Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            NhanVien nhanVien = nhanVienService.findByUserId(user.getId());

            if (nhanVien == null) {
                return ResponseEntity.badRequest().body("Không tìm thấy nhân viên tương ứng với user");
            }

            // Kiểm tra công việc
            if (chamCong.getCongViec() == null || chamCong.getCongViec().getId() == null) {
                return ResponseEntity.badRequest().body("Thiếu thông tin công việc.");
            }

            CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
            if (congViec == null) {
                return ResponseEntity.badRequest().body("Công việc không tồn tại.");
            }

            chamCong.setCongViec(congViec);
            chamCong.setNhanVien(nhanVien);

            ChamCong saved = chamCongService.save(chamCong);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Lỗi khi tạo chấm công: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChamCong> updateChamCong(@PathVariable Long id, @RequestBody ChamCong chamCong) {
        ChamCong existing = chamCongService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Nếu cập nhật công việc
        if (chamCong.getCongViec() != null && chamCong.getCongViec().getId() != null) {
            CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
            if (congViec != null) {
                chamCong.setCongViec(congViec);
            }
        }

        chamCong.setId(id);
        ChamCong updated = chamCongService.save(chamCong);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChamCong(@PathVariable Long id) {
        ChamCong existing = chamCongService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        chamCongService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<ChamCong>> getMyChamCongHistory(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        NhanVien nv = nhanVienService.findByUserUsername(username);
        if (nv == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<ChamCong> list = chamCongService.findByNhanVienId(nv.getId());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/nhanvien/{id}")
    public ResponseEntity<List<ChamCong>> getChamCongByNhanVienId(@PathVariable Long id) {
        List<ChamCong> list = chamCongService.findByNhanVienId(id);
        return ResponseEntity.ok(list);
    }
}
