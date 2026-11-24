package com.minhphap.english4life;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModularityTests {
    ApplicationModules modules = ApplicationModules.of(English4lifeApplication.class);

    @Test
    void verifiesModularStructure() {
        modules.verify();
    }

    @Test
    void printsModules() {
        modules.forEach(System.out::println);
    }
}
