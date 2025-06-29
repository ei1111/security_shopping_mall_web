package com.web.item.controller.api;

import com.web.item.form.ItemRequest;
import com.web.item.form.ItemResponse;
import com.web.item.service.ItemSerivce;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item/v1")
@Tag(name = "2. 상품 API")
public class ItemApiController {
    private final ItemSerivce itemSerivce;


    @PostMapping("/new")
    @Operation(summary = "상품 정보 등록 API")
    public void save(
            @RequestPart("image") MultipartFile imageFile, // 파일 받기
            @RequestPart("data") @Valid ItemRequest request // JSON 데이터 받기
    ) {
        itemSerivce.save(imageFile , request);
    }


    @GetMapping("/list")
    @Operation(summary = "상품 리스트 API")
    public ResponseEntity<List<ItemResponse>> itemList() {
        return ResponseEntity.ok(itemSerivce.findAll());
    }

    @GetMapping("/{itemId}/edit")
    @Operation(summary = "상품 조회 API")
    public ResponseEntity<ItemResponse> itemList(@PathVariable Long itemId) {
        return ResponseEntity.ok(itemSerivce.findById(itemId));
    }

    @PutMapping("/{itemId}/edit")
    @Operation(summary = "상품 수정 API")
    public void update(
            @PathVariable Long itemId,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestPart("data") ItemRequest request
    ) {
        itemSerivce.update(itemId, imageFile, request);
    }
}
