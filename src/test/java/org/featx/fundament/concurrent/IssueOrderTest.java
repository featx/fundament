package org.featx.fundament.concurrent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IssueOrderTest {
    @Test
    void testIssueOrder() {
        while (true)
            try {
                Integer result = new IssueOrder().invoke();
                assertNotEquals(1, result);
            } catch (Exception e) {
                assertNull(e);
            }
    }
}
