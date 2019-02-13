package ca.ualberta.cs.lonelytwitter;

import org.junit.Test;

import ca.ualberta.cs.lonelytwitter.models.Tweet;

import static org.junit.Assert.assertEquals;


public class getCountTest {
    @Test
    public void testCount() {
        LonelyTwitterActivity l = new LonelyTwitterActivity();
        Tweet t1 = new Tweet("HELLO");
        l.addTweet(t1);
        int c = l.getCount();
        assertEquals(1, c);
    }
}
