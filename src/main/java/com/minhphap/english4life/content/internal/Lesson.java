package com.minhphap.english4life.content.internal;

import jakarta.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false, length = 200)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LessonStatus status = LessonStatus.DRAFT;

    @Column(name = "display_order", nullable = false)
    private int displayOrder;

    protected Lesson() {
    }

    public Lesson(int displayOrder, LessonStatus status, String title, Topic topic) {
        this.displayOrder = displayOrder;
        this.status = status;
        this.title = title;
        this.topic = topic;
    }

    public Long getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getTitle() {
        return title;
    }

    public LessonStatus getStatus() {
        return status;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void publish() {
        this.status = LessonStatus.PUBLISHED;
    }
}
