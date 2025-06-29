package com.web.item.service;

import com.web.item.domain.Item;
import com.web.item.form.ItemRequest;
import com.web.item.form.ItemResponse;
import com.web.item.repository.ItemRepository;
import jakarta.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemSerivce {
    private final ItemRepository itemRepository;
    private static final String UPLOAD_PATH = "/Users/manjae/Documents/code/spring_projdct/web_project/upload/";

    @Transactional
    public void save(MultipartFile imageFile, ItemRequest request) {
            String imagePath = getImagePath(imageFile);
            itemRepository.save(Item.from(request, imagePath));

    }


    public List<ItemResponse> findAll() {
        AtomicInteger rowNum = new AtomicInteger(1);
        return itemRepository.findAll().stream()
                .map(item -> ItemResponse.from(item, rowNum))
                .toList();
    }

    public ItemResponse findById(Long itemId) {
        return itemRepository.findById(itemId)
                .map(ItemResponse::from)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void update(Long itemId, MultipartFile imageFile, @Valid ItemRequest request) {
        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        String imagePath = null;
        // 이미지가 새로 업로드된 경우만 파일 저장
        if (Objects.nonNull(imageFile)) {
                imagePath = getImagePath(imageFile);
        }

        item.updateForm(request, imagePath);
    }

    private String getImagePath(MultipartFile imageFile) {
        // 1. 저장할 파일명 생성 (파일명이 겹치지 않도록 UUID 추가)
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        // 2. 저장할 경로 지정 (예: C:/upload)
        File dir = new File(UPLOAD_PATH);

        // 👉 디렉토리 없으면 생성
        if (!dir.exists()) {
            dir.mkdirs(); // mkdirs()는 하위 폴더까지 모두 생성
        }

        File uploadFile = new File(UPLOAD_PATH + fileName);

        // 3. 파일 저장
        try {
            imageFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 4. DB에 저장 (파일의 상대 경로 또는 URL 저장)
        // 프론트에서 접근할 URL
        return "/images/" + fileName;
    }
}
