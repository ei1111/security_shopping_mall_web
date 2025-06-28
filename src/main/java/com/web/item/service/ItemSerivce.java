package com.web.item.service;

import com.web.item.domain.Item;
import com.web.item.form.ItemRequest;
import com.web.item.form.ItemResponse;
import com.web.item.repository.ItemRepository;
import jakarta.validation.Valid;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemSerivce {
    private final ItemRepository itemRepository;

    @Transactional
    public void save(ItemRequest request) {
        itemRepository.save(Item.from(request));
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
    public void update(Long itemId, @Valid ItemRequest request) {
        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        item.updateForm(request);
    }
}
