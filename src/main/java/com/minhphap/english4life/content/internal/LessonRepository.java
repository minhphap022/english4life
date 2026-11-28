package com.minhphap.english4life.content.internal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTopicIdAndStatusOrderByDisplayOrder(Long topicId, LessonStatus status);
}
