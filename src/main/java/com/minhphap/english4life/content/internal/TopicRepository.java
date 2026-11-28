package com.minhphap.english4life.content.internal;

import com.minhphap.english4life.content.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByLevelOrderByDisplayOrder(Level level);
    Optional<Topic> findByLevelAndSlug(Level level, String slug);
}
