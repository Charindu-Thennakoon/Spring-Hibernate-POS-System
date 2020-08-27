package com.myorg.dummy.pos.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements SuperEntity {
    @Id
    private String id;
    private String name;
    private String address;
}
