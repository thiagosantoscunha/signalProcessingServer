package com.aimbra.paralellus.apis;

import com.aimbra.paralellus.models.DataResponse;
import com.aimbra.paralellus.models.PayloadInnerFile;
import com.aimbra.paralellus.services.ImageEffectService;
import com.aimbra.paralellus.services.ImageSerialProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/api/file")
public class FileApi {

    @Autowired
    private ImageSerialProcessService service;

    @Autowired
    private ImageEffectService imageEffectService;

    @PostMapping
    public ResponseEntity<DataResponse> serialProccess(@RequestBody PayloadInnerFile file) {
        DataResponse dataResponse = service.serialProcess(file);
        return ResponseEntity.ok(dataResponse);
    }

    @GetMapping
    public ResponseEntity<DataResponse> lightGray() {
        LocalTime start = LocalTime.now();
        String base64 = imageEffectService.setParams(null, null).toGrayscale().getBase64();
        LocalTime end = LocalTime.now();
        DataResponse dataResponse = new DataResponse(start, end, end.getNano() - start.getNano(), new String(base64));
        return ResponseEntity.ok(dataResponse);
    }

}
