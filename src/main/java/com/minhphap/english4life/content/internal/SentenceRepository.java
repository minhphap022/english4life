package com.minhphap.english4life.content.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface SentenceRepository extends JpaRepository<Sentence, Long> {
    List<Sentence> findByLessonIdOrderByPosition(Long lessonId);
}
