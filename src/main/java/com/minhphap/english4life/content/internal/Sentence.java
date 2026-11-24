package com.minhphap.english4life.content.internal;

import jakarta.persistence.*;

@Entity
@Table(name = "sentences")
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private int position;

    @Column(name = "text_en", nullable = false, columnDefinition = "text")
    private String textEn;

    @Column(name = "meaning_vi", columnDefinition = "text")
    private String meaningVi;

    protected Sentence() {
    }

    public Sentence(String meaningVi, String textEn, int position, Lesson lesson) {
        this.meaningVi = meaningVi;
        this.textEn = textEn;
        this.position = position;
        this.lesson = lesson;
    }

    public String getMeaningVi() {
        return meaningVi;
    }

    public String getTextEn() {
        return textEn;
    }

    public int getPosition() {
        return position;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Long getId() {
        return id;
    }
}
