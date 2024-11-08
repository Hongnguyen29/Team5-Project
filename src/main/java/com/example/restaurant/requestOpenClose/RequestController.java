package com.example.restaurant.requestOpenClose;

import com.example.restaurant.enumList.RequestStatus;
import com.example.restaurant.requestOpenClose.dto.CloseViewDto;
import com.example.restaurant.requestOpenClose.dto.ConfirmDto;
import com.example.restaurant.requestOpenClose.dto.OpenDto;
import com.example.restaurant.requestOpenClose.dto.OpenViewDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;

    @PostMapping("/user/openRequest")
    public ResponseEntity<OpenViewDto> openShop(
            @ModelAttribute OpenDto dto
    ) {
        OpenViewDto openViewDto = requestService.openRestaurant(dto);
        log.info("loi controller");
        return ResponseEntity.ok(openViewDto);
    }

    @PutMapping("/admin/open/confirm/{openId}")
    public ResponseEntity<OpenViewDto> confirmRequest(
            @PathVariable Long openId,
            @RequestBody ConfirmDto dto
    ) {
        OpenViewDto openViewDto = requestService.openConfirm(openId, dto);
        return ResponseEntity.ok(openViewDto);
    }

    @GetMapping("/auth/opens/{openId}")
    public ResponseEntity<OpenViewDto> readOne(
            @PathVariable Long openId
    ) {
        OpenViewDto dto = requestService.readOne(openId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/admin/opens/ReadAll")
    public ResponseEntity<List<OpenViewDto>> adminReadAll(
            @RequestParam(required = false)
            RequestStatus status
    ) {
        List<OpenViewDto> dtoList = requestService.adminReadAll(status);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/auth/opens/readAll")
    public ResponseEntity<List<OpenViewDto>> userReadAll() {
        List<OpenViewDto> dtoList = requestService.userReadAll();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/rest/close")
    public ResponseEntity<CloseViewDto> closeRestaurant(
            @RequestParam String reason
    ) {
        log.info("loi con");
        CloseViewDto closeViewDto = requestService.closeRestaurant(reason);
        return ResponseEntity.ok(closeViewDto);
    }

    @PutMapping("/admin/close/confirm/{closeId}")
    public ResponseEntity<CloseViewDto> closeConfirm(
            @PathVariable Long closeId
    ) {
        CloseViewDto closeViewDto = requestService.closeConfirm(closeId);
        return ResponseEntity.ok(closeViewDto);
    }

    @GetMapping("/auth/close/{closeId}")
    public ResponseEntity<CloseViewDto> readOneClose(
            @PathVariable Long closeId
    ) {
        CloseViewDto dto = requestService.readOneClose(closeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/auth/close/readAll")
    public ResponseEntity<List<CloseViewDto>> userReadAllClose() {
        List<CloseViewDto> dtoList = requestService.userReadAllClose();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/admin/close/ReadAll")
    public ResponseEntity<List<CloseViewDto>> adminReadAllClose(
            @RequestParam(required = false)
            RequestStatus status
    ) {
        List<CloseViewDto> dtoList = requestService.adminReadAllClose(status);
        return ResponseEntity.ok(dtoList);
    }

}
