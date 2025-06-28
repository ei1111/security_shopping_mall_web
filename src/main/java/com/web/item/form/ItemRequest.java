package com.web.item.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "상품 항목 request")
public record ItemRequest(
        @Schema(description = "상품 이름", example = "초코우유")
        @NotBlank(message = "상품명은 필수 항목입니다.")
        String name,

        @Schema(description = "상품 가격", example = "2000")
        int price,

        @Schema(description = "상품 수량", example = "10")
        int stockQuantity,

        @Schema(description = "상품 저자", example = "저자")
        String author,

        @Schema(description = "상품 isbn", example = "isbn")
        String isbn
)
{
}
