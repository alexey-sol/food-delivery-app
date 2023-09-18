package com.github.alexeysol.app.feature.place.model.entity;

import com.github.alexeysol.app.feature.city.model.entity.City;
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

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @OneToOne(mappedBy = "address")
    private Place place;
}
