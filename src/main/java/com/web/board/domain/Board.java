package com.web.board.domain;

import com.web.audit.BaseEntity;
import com.web.board.form.BoardRequest;
import com.web.board.form.BoardResponse;
import com.web.member.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("제목")
    private String title;

    @Lob
    @Comment("내용")
    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public static Board from(BoardRequest boardRequest,Member member ) {
        return new Board(boardRequest.boardId, boardRequest.title, boardRequest.content, member);
    }

    public Board updateForm(BoardRequest boardRequest) {
        if(!Objects.equals(boardRequest.title, this.title)) {
            this.title = boardRequest.title;
        }

        if(!Objects.equals(boardRequest.content, this.content)) {
            this.content = boardRequest.content;
        }

        return this;
    }
}
