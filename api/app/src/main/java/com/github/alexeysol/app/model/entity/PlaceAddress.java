package com.github.alexeysol.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "place_address")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "place_address_seq")
    @SequenceGenerator(name = "place_address_seq", sequenceName = "place_address_seq", allocationSize = 1)
    private long id;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne(mappedBy = "address")
    private Place place;
}
