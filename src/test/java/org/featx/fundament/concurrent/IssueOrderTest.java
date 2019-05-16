package org.featx.fundament.concurrent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class IssueOrderTest {
    private IssueOrder issueOrder = new IssueOrder();

    @Test
    void testIssueOrder() {
        String result = "";
        while (!"(0, 0)".equals(result)){
            try {
                issueOrder.init();
                result = issueOrder.invoke();
                assertNotEquals("(0, 0)", result);
            } catch (Exception e) {
                assertNull(e);
            }
        }
    }
}
