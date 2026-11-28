package com.minhphap.english4life.content.internal;

import com.minhphap.english4life.content.Level;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ContentRepositoriesTest {
    @Autowired
    TopicRepository topics;

    @Autowired
    LessonRepository lessons;

    @Autowired
    SentenceRepository sentences;

    @Test
    void returnsOnlyPublishedLessonsInDisplayOrder() {
        Topic topic = topics.save(new Topic(Level.A1, "greetings", "Greetings", 1));
        Lesson second = lessons.save(new Lesson(topic, "Second", 2));
        Lesson first = lessons.save(new Lesson(topic, "First", 1));
        lessons.save(new Lesson(topic, "Still a draft", 3));
        first.publish();
        second.publish();

        List<Lesson> result =
                lessons.findByTopicIdAndStatusOrderByDisplayOrder(topic.getId(), LessonStatus.PUBLISHED);
        assertThat(result).extracting(Lesson::getTitle).containsExactly("First", "Second");
    }

    @Test
    void returnsSentencesInPositionOrder() {
        Topic topic = topics.save(new Topic(Level.A1, "food", "Food", 1));
        Lesson lesson = lessons.save(new Lesson(topic, "At a cafe", 1));

        sentences.save(new Sentence(lesson, 2, "I would like a coffee.", null));
        sentences.save(new Sentence(lesson, 1, "Hello, can I help you?", "Xin chào, tôi giúp được gì?"));

        List<Sentence> result = sentences.findByLessonIdOrderByPosition(lesson.getId());

        assertThat(result).extracting(Sentence::getPosition).containsExactly(1, 2);
    }
}
