package com.tcs.Library.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity
public class Author {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long Id;
    @Column(name = "author_name")
    private String name;
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "public_id", unique = true, nullable = false, updatable = false)
    private String publicId;

    @ManyToMany(mappedBy = "authors")
    private List<Book> book;

    @PrePersist
    public void generatePublicId() {
        if (publicId == null) {
            publicId = java.util.UUID.randomUUID().toString();
        }
    }
}
