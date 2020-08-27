package com.myorg.dummy.pos.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements SuperEntity {

    @EmbeddedId
    private OrderDetailPK orderDetailPK;
    private int qty;
    private BigDecimal unitPrice;
    @ManyToOne
    @JoinColumn(name = "orderId", referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "itemCode", referencedColumnName = "code", insertable = false, updatable = false)
    private Item item;

    public OrderDetail(String orderId, String itemCode, int qty, BigDecimal unitPrice) {
        this.orderDetailPK = new OrderDetailPK(orderId, itemCode);
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public OrderDetail(OrderDetailPK orderDetailPK, int qty, BigDecimal unitPrice) {
        this.orderDetailPK = orderDetailPK;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }
}
