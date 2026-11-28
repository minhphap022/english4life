package com.minhphap.english4life.content;

import java.util.List;

public interface TopicCatalog {
    List<TopicSummary> findByLevel(Level level);
}
