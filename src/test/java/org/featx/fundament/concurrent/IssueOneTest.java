package org.featx.fundament.concurrent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class IssueOneTest {

    @Test
    void testIssueOne() {
        try {
            Integer result = new IssueOne().invoke();
            assertEquals(2000000, result);
        } catch (Exception e) {
            assertNull(e);
        }
    }
}
