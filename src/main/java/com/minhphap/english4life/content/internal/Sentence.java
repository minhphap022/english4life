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

    public Sentence(Lesson lesson, int position, String textEn, String meaningVi) {
        this.lesson = lesson;
        this.position = position;
        this.textEn = textEn;
        this.meaningVi = meaningVi;
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
