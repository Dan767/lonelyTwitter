package ca.ualberta.cs.lonelytwitter;

import org.junit.Test;

import java.util.ArrayList;

import ca.ualberta.cs.lonelytwitter.models.Tweet;

import static org.junit.Assert.assertEquals;

public class getTweetsTest {
    @Test
    public void testTweets() {
        LonelyTwitterActivity l = new LonelyTwitterActivity();
        Tweet t1 = new Tweet("HELLO");
        l.addTweet(t1);

        ArrayList<Tweet> c = new ArrayList<Tweet>();
        c.add(t1);

        ArrayList<Tweet> b = l.getTweets();
        assertEquals(c , b);
    }
}
