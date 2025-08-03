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

            // Nếu trạng thái là "DI_LAM" thì cần công việc
            if ("DI_LAM".equalsIgnoreCase(chamCong.getTrangThai())) {
                if (chamCong.getCongViec() == null || chamCong.getCongViec().getId() == null) {
                    return ResponseEntity.badRequest().body("Thiếu thông tin công việc khi đi làm.");
                }

                CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
                if (congViec == null) {
                    return ResponseEntity.badRequest().body("Công việc không tồn tại.");
                }

                chamCong.setCongViec(congViec);
            } else {
                chamCong.setCongViec(null); // nghỉ phép thì không cần công việc
            }

            chamCong.setNhanVien(nhanVien);

            ChamCong saved = chamCongService.save(chamCong);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log chi tiết
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tạo chấm công: " + e.getMessage());
        }
    }

    @PostMapping("/ca-nhan")
    public ResponseEntity<?> createChamCongCaNhan(@RequestBody ChamCong chamCong, Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();


            if ("DI_LAM".equalsIgnoreCase(chamCong.getTrangThai())) {
                if (chamCong.getCongViec() == null || chamCong.getCongViec().getId() == null) {
                    return ResponseEntity.badRequest().body("Thiếu thông tin công việc khi đi làm.");
                }

                CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
                if (congViec == null) {
                    return ResponseEntity.badRequest().body("Công việc không tồn tại.");
                }

                chamCong.setCongViec(congViec);
            } else {
                chamCong.setCongViec(null);
            }

            // Nếu user chưa gán với nhân viên, vẫn cho phép chấm công cá nhân
            NhanVien nhanVien = nhanVienService.findByUserId(user.getId());
            if (nhanVien != null) {
                chamCong.setNhanVien(nhanVien);
            }

            chamCong.setUser(user);

            ChamCong saved = chamCongService.save(chamCong);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log rõ ràng
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi tạo chấm công: " + e.getMessage());
        }
    }

    @GetMapping("/tong-tien-cong/{userId}")
    public ResponseEntity<Double> tongTienCong(@PathVariable Long userId) {
        double tong = chamCongService.tinhTongTienCongTheoUserId(userId);
        return ResponseEntity.ok(tong);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ChamCong> updateChamCong(@PathVariable Long id, @RequestBody ChamCong chamCong) {
        ChamCong existing = chamCongService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        if ("DI_LAM".equalsIgnoreCase(chamCong.getTrangThai())) {
            if (chamCong.getCongViec() != null && chamCong.getCongViec().getId() != null) {
                CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
                if (congViec != null) {
                    chamCong.setCongViec(congViec);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            chamCong.setCongViec(null);
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

    @GetMapping("/ca-nhan")
    public ResponseEntity<?> getChamCongCaNhan(Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();

            System.out.println("🔍 User ID từ token: " + userId);
            List<ChamCong> list = chamCongService.findByUserId(userId);

            System.out.println("📦 Số bản ghi tìm được: " + list.size());
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi lấy dữ liệu cá nhân");
        }
    }


    @PutMapping("/ca-nhan/{id}")
    public ResponseEntity<?> updateChamCongCaNhan(
            @PathVariable Long id,
            @RequestBody ChamCong chamCong,
            Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        ChamCong existing = chamCongService.findById(id);
        if (existing == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy bản ghi chấm công.");
        }

        // Đảm bảo chỉ cho phép người tạo (user) cập nhật bản ghi của mình
        if (existing.getUser() == null || !existing.getUser().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền sửa bản ghi này.");
        }

        try {
            if ("DI_LAM".equalsIgnoreCase(chamCong.getTrangThai())) {
                if (chamCong.getCongViec() == null || chamCong.getCongViec().getId() == null) {
                    return ResponseEntity.badRequest().body("Thiếu thông tin công việc khi đi làm.");
                }

                CongViec congViec = congViecService.findById(chamCong.getCongViec().getId());
                if (congViec == null) {
                    return ResponseEntity.badRequest().body("Công việc không tồn tại.");
                }

                existing.setCongViec(congViec);
            } else {
                existing.setCongViec(null);
            }

            existing.setNgayCham(chamCong.getNgayCham());
            existing.setTrangThai(chamCong.getTrangThai());
            existing.setGhiChu(chamCong.getGhiChu());
            existing.setTienCong(chamCong.getTienCong());

            ChamCong updated = chamCongService.save(existing);
            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi khi cập nhật chấm công: " + e.getMessage());
        }
    }




    @GetMapping("/nhanvien/{id}")
    public ResponseEntity<List<ChamCong>> getChamCongByNhanVienId(@PathVariable Long id) {
        List<ChamCong> list = chamCongService.findByNhanVienId(id);
        return ResponseEntity.ok(list);
    }
}
