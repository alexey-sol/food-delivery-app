package com.github.alexeysol.fooddelivery.feature.user.model.entity;

import com.github.alexeysol.fooddelivery.feature.city.model.entity.City;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_address")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_address_seq")
    @SequenceGenerator(name = "user_address_seq", sequenceName = "user_address_seq", allocationSize = 1)
    private long id;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @Column(name = "address_line", nullable = false)
    private String addressLine;

    @OneToOne(mappedBy = "address")
    private User user;
}
