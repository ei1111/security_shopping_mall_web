package com.web.board.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@Schema(description = "게시판 항목")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardRequest {
    @Schema(description = "게시판 Id")
    public Long boardId;

    @Schema(description = "제목")
    @NotBlank(message = "제목은 필수 입니다.")
    public String title;

    @Schema(description = "내용")
    @NotBlank(message = "내용은 필수 입니다.")
    public String content;
}
