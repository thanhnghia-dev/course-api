package vn.edu.luphung.courseapi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.luphung.courseapi.util.BarcodeGenerator;

@RestController
@RequestMapping("api/v1/public/barcodes")
public class BarcodeController {

    @GetMapping("{studentId}")
    public ResponseEntity<byte[]> getBarcode(@PathVariable String studentId) throws Exception {
        byte[] image = BarcodeGenerator.generateBarcodeWithText(studentId, 300, 100);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + studentId + ".png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
