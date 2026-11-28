package com.minhphap.english4life.content.internal;


import com.minhphap.english4life.content.Level;
import com.minhphap.english4life.content.TopicCatalog;
import com.minhphap.english4life.content.TopicSummary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
class TopicCatalogService implements TopicCatalog {
    private final TopicRepository topics;

    TopicCatalogService(TopicRepository topics) {
        this.topics = topics;
    }

    @Override
    public List<TopicSummary> findByLevel(Level level) {
        return topics.findByLevelOrderByDisplayOrder(level).stream()
                .map(t -> new TopicSummary(t.getId(), t.getLevel(), t.getSlug(), t.getTitle()))
                .toList();
    }
}
