package com.example.lesure.security.auth.controller;


import com.example.lesure.domain.base.AbstractIdentifiable;
import com.example.lesure.domain.constants.Permission;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends AbstractIdentifiable {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "role_permissions")
    @Column(name = "permission")
    private Set<Permission> permissions = new HashSet<>();
}
