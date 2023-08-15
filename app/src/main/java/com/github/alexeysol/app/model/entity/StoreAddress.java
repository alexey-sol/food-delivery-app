package com.github.alexeysol.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_address")
@Data
@EqualsAndHashCode(exclude = "store")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String city;

    @OneToOne(mappedBy = "address")
    private Store store;
}
