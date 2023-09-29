package com.github.alexeysol.fooddelivery.feature.place.model.entity;

import com.github.alexeysol.fooddelivery.feature.city.model.entity.City;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "place_address",
    indexes = {
        @Index(columnList = "city_id"),
    })
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @OneToOne(mappedBy = "address")
    private Place place;
}
