package com.web.board.domain;

import com.web.audit.BaseEntity;
import com.web.board.form.BoardRequest;
import com.web.board.form.BoardResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Comment("제목")
    private String title;

    @Lob
    @Comment("내용")
    private String content;

    public static Board from(BoardRequest boardRequest) {
        return new Board(boardRequest.boardId, boardRequest.title, boardRequest.content);
    }

    public void updateForm(BoardRequest boardRequest) {
        if(!Objects.equals(boardRequest.title, this.title)) {
            this.title = boardRequest.title;
        }

        if(!Objects.equals(boardRequest.content, this.content)) {
            this.content = boardRequest.content;
        }
    }
}
