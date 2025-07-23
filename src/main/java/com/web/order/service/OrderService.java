package com.web.order.service;

import com.web.delivery.entity.Delivery;
import com.web.global.common.util.SecurityUtill;
import com.web.item.entity.Item;
import com.web.item.repository.ItemRepository;
import com.web.member.entity.Member;
import com.web.member.repository.MemberRepository;
import com.web.order.entity.Order;
import com.web.order.form.OrderResponse;
import com.web.order.form.OrderSearchRequest;
import com.web.order.repository.OrderRepository;
import com.web.orderItem.entity.OrderItem;
import com.web.payment.entity.Payment;
import com.web.payment.facade.CancelFacadeEvent;
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
    private final CancelFacadeEvent cancelFacadeEvent;


    @Transactional
    public void order(Long itemId, int count) {
        //비관적락으로 재고 감소 문제 해결
        Item item = itemRepository.findByIdPerssimsticLock(itemId).orElseThrow(IllegalArgumentException::new);
        Member member = memberRepository.findByUserId(SecurityUtill.getUserId());

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
                    cancelFacadeEvent.cancelEvent(impUid);
                });
    }
}
