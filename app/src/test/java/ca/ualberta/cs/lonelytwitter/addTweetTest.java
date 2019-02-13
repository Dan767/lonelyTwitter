package ca.ualberta.cs.lonelytwitter;

import org.junit.Test;

import ca.ualberta.cs.lonelytwitter.models.Tweet;

import static org.junit.Assert.assertEquals;

public class addTweetTest {
    @Test
    public void testCount() {
        LonelyTwitterActivity l = new LonelyTwitterActivity();
        Tweet t1 = new Tweet("HELLO");
        l.addTweet(t1);

        try {
            l.addTweet(t1);
        }
        catch (IllegalArgumentException e) {
            assertEquals(1, 1);
            return;
        }
        finally {
            assertEquals(1, -1);
            return;
        }
    }
}
