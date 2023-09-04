package com.github.alexeysol.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_address")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_address_seq")
    @SequenceGenerator(name = "store_address_seq", sequenceName = "store_address_seq", allocationSize = 1)
    private long id;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne(mappedBy = "address")
    private Store store;
}
