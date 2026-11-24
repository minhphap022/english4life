package com.minhphap.english4life.content.internal;

import jakarta.persistence.*;


@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private Level level;

    @Column(nullable = false, length = 100)
    private String slug;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    protected Topic() {
    }

    public Topic(Level level, String slug, String title, int displayOrder) {
        this.level = level;
        this.slug = slug;
        this.title = title;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public Level getLevel() {
        return level;
    }

    public String getSlug() {
        return slug;
    }

    public String getTitle() {
        return title;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }
}
