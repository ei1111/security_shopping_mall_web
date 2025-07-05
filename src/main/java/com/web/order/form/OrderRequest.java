package com.web.order.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(
        @NotNull
        @Positive
        @Schema(description = "상품 번호", example = "1")
        Long itemId,

        @NotNull
        @Positive
        @Schema(description = "주문 수량", example = "5")
        int count
) {}
