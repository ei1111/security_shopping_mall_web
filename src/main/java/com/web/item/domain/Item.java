package com.web.item.domain;


import com.web.audit.BaseEntity;
import com.web.error.errorCode.ErrorCode;
import com.web.error.exception.BusinessException;
import com.web.item.form.ItemRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseEntity {
    @Id
    @Comment( "상품 번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Comment( "상품 이름")
    private String name;

    @Comment( "상품 가격")
    private int price;

    @Comment( "상품 수량")
    private int stockQuantity;

    @Comment( "상품 저자")
    private String author;

    @Comment( "상품 isbn")
    private String isbn;

    private Item(ItemRequest request) {
        this.name = request.name();
        this.price = request.price();
        this.stockQuantity = request.stockQuantity();
        this.author = request.author();
        this.isbn = request.isbn();
    }

    public static Item from(ItemRequest request) {
        return new Item(request);
    }

    public void updateForm(ItemRequest request) {
        updateIfChanged(request.name(), this.name, v -> this.name = v);
        updateIfChanged(request.price(), this.price, v -> this.price = v);
        updateIfChanged(request.stockQuantity(), this.stockQuantity, v -> this.stockQuantity = v);
        updateIfChanged(request.author(), this.author, v -> this.author = v);
        updateIfChanged(request.isbn(), this.isbn, v -> this.isbn = v);
    }

    private <T> void updateIfChanged(T newValue, T oldValue, Consumer<T> consumer) {
        if (!Objects.equals(newValue, oldValue)) {
            consumer.accept(newValue);
        }
    }

    public void removeStock(int count) {
        int reultStock = this.stockQuantity - count;

        if (reultStock < 0) {
            throw new BusinessException(ErrorCode.ITEM_STOCK_NOT_ENOUGH);
        }

        this.stockQuantity -= count;
    }

    public void addStock(int count) {
        this.stockQuantity += count;
    }
}
