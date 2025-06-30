package com.web.order.service;

import com.web.delivery.domain.Delivery;
import com.web.item.domain.Item;
import com.web.item.repository.ItemRepository;
import com.web.member.domain.Member;
import com.web.member.repository.MemberRepository;
import com.web.order.domain.Order;
import com.web.order.form.OrderResponse;
import com.web.order.form.OrderSearchRequest;
import com.web.order.repository.OrderRepository;
import com.web.orderItem.domain.OrderItem;
import com.web.payment.domain.Payment;
import com.web.payment.repository.PaymentRepository;
import com.web.payment.service.PaymentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;


    @Transactional
    public void order(Long memberId, Long itemId, int count) {
        Item item = itemRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(IllegalArgumentException::new);

        Delivery delivery = new Delivery(member.getAddress());
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.from(member, delivery, orderItem);
        orderRepository.save(order);
    }

    public List<OrderResponse> orderSearch(OrderSearchRequest request) {
        return orderRepository.orderSearch(request);
    }

    @Transactional
    public void cancel(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(IllegalArgumentException::new);
        order.cancel();

        paymentRepository.findByOrder(order)
                .map(Payment::getImpUid)
                .ifPresent(impUid -> {
                    paymentService.cancel(impUid);
                });
    }
}
