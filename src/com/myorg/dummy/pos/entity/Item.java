package com.myorg.dummy.pos.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item implements SuperEntity {
    @Id
    private String code;
    private String description;
    private BigDecimal unitPrice;
    private int qtyOnHand;
}
