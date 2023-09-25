package com.github.alexeysol.fooddelivery.feature.city.model.entity;

import com.github.alexeysol.fooddelivery.feature.place.model.entity.PlaceAddress;
import com.github.alexeysol.fooddelivery.feature.user.model.entity.UserAddress;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
    @SequenceGenerator(name = "city_seq", sequenceName = "city_seq", allocationSize = 1)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "city")
    private Set<UserAddress> userAddresses = new HashSet<>();

    @OneToMany(mappedBy = "city")
    private Set<PlaceAddress> placeAddresses = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Date updatedAt;
}
