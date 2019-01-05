package de.neebs.indexing.model.event;

import org.junit.Test;

import java.util.Date;

public class RoboIndexReviewProcessorTest {
    @Test
    public void testGetNextReviewDate() {
        System.out.println(new RoboIndexReviewProcessor().getNextReviewDate(new Date()));
    }
}
