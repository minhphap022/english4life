package com.minhphap.english4life.content.internal;

import com.minhphap.english4life.content.Level;
import com.minhphap.english4life.content.TopicCatalog;
import com.minhphap.english4life.content.TopicSummary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
class TopicController {
    private final TopicCatalog catalog;

    TopicController(TopicCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping
    List<TopicSummary> list(@RequestParam Level level) {
        return catalog.findByLevel(level);
    }
}
