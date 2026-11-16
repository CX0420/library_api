package com.library.library_api.entity;

import com.library.library_api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "borrower")
@Getter
@Setter
@NoArgsConstructor
public class Borrower extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "email_address", nullable = false,
            unique = true, length = 255)
    private String emailAddress;

    @Column(nullable = false, length = 20)
    private String status = "ACTIVE";
}
