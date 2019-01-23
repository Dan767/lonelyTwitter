/**
 * CMPUT 301
 *
 * Version 2.0 - Added data persistence with JSON
 *
 * January 22, 2019
 *
 * Copyright CMPUT 301 Daniel Dick 2019
 */

package ca.ualberta.cs.lonelytwitter.models;

import java.util.Date;


/**
 * @author Daniel Dick
 * @version 2.0
 * @see 1.0: https://github.com/hazelybell/lonelyTwitter
 * Tweet is the class for all messages "tweeted" in the LonelyTwitter
 * app.  A Tweet object encapsulates all information required for a
 * tweet to be stored and maintained in the app.
 */
public class Tweet {

    private String message;
    Date date;

    /**
     * Constructor for the tweet class. Sets the message.
     * @param message
     */
    public Tweet(String message) {

        this.message = message;
    }

    /**
     * Setter for message.
     * @param message
     */
    public void setMessage (String message) {

        this.message = message;
    }

    /**
     * getter for message.
     * @return message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Override, when this class is converted to string
     * it will return the message string created in the
     * constructor.
     * @return message
     */
    @Override
    public String toString() {
        return message;
    }

}
