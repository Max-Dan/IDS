package it.unicam.cs.ids.lp.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;

    @PutMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRequest adminRequest) {
        Admin admin = adminMapper.apply(adminRequest);
        boolean registered = adminService.registerAdmin(admin);
        if (!registered)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/unregister/{adminId}")
    public ResponseEntity<?> unregisterAdmin(@PathVariable long adminId) {
        adminService.unregisterAdmin(adminId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

