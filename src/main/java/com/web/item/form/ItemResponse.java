package com.web.item.form;

import com.web.item.domain.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "상품 항목 response")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponse {

    @Schema(name = "상품 번호")
    private Long itemId;

    @Schema(name = "상품 이름")
    private String name;

    @Schema(name = "상품 가격")
    private int price;

    @Schema(name = "상품 수량")
    private int stockQuantity;

    @Schema(name = "상품 저자")
    private String author;

    @Schema(name = "상품 isbn")
    private String isbn;

    @Schema(name = "게시글 순서")
    public Integer rowNum;

    @Schema(name = "이미지 주소")
    public String imagePath;


    private ItemResponse(Item item) {
        this.itemId = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
        this.author = item.getAuthor();
        this.isbn = item.getIsbn();
        this.imagePath =  item.getImagePath();
    }

    public static ItemResponse from(Item item, AtomicInteger rowNum) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), item.getStockQuantity(), item.getAuthor(), item.getIsbn(), rowNum.getAndIncrement(), item.getImagePath());
    }

    public static ItemResponse from(Item item) {
        return new ItemResponse(item);
    }

}
