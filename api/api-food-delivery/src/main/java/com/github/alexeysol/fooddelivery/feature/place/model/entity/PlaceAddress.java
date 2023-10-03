package com.github.alexeysol.fooddelivery.feature.place.model.entity;

import com.github.alexeysol.fooddelivery.feature.locality.model.entity.Locality;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "place_address",
    indexes = {
        @Index(columnList = "locality_id"),
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
    @JoinColumn(name = "locality_id", referencedColumnName = "id")
    private Locality locality;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @OneToOne(mappedBy = "address")
    private Place place;
}
