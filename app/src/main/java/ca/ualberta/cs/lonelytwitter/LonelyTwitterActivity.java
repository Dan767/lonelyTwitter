package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ca.ualberta.cs.lonelytwitter.models.Tweet;


/**
 * @author Daniel Dick
 * @author CMPUT 301 staff, U of A
 * @version 2.0
 * @see 1.0: https://github.com/hazelybell/lonelyTwitter
 * LonelyTwitterActivity extends the Activity class used
 * in Android.  This allows for all of the operations of
 * the LonelyTwitter application in the Android operating
 * environment.  All actions and operations of the app
 * are contained here.
 */
public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file5.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
	private int tweetcounter;
	private ArrayAdapter<Tweet> adapter;
	Firebase tweetchild;
	/** Called when the activity is first created. */
	/**
	 * Overrides the onCreate functionality in Android,
	 * runs the basic behaviour of the application including
	 * what actions to take and what inputs to monitor.
	 * All operation of the application occurs here.
	 * @param savedInstanceState
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		tweetcounter = 0;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Firebase.setAndroidContext(this);

		final Firebase ref  = new Firebase("https://lonelytwitter-23e7c.firebaseio.com/");
		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * This function enables the "send" button for the application.
			 * When the button is pressed, this function will take the
			 * text from the input box, add it as a Tweet to the list of
			 * tweets, and cause a display update.
			 * @param v
			 */
			public void onClick(View v) {
				tweetcounter += 1;
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();

				//tweetchild.setValue(text);

				Tweet tweet = new Tweet(text);
				tweetchild = ref.child(tweet.getUuid().toString());
				tweetList.add(tweet);
				tweetchild.setValue(tweet);
                adapter.notifyDataSetChanged();
                saveInFile();
			}
		});

		ref.addValueEventListener(new ValueEventListener() {
			public void onDataChange(DataSnapshot snapshot) {
			tweetList.clear();
				for (DataSnapshot d: snapshot.child("Tweet").getChildren()) {
					Tweet temp = d.getValue(Tweet.class);
					if (temp.getMessage().substring(1,2).equals("U")) {
						temp.setMessage(temp.getMessage().toUpperCase());
					}
					else if (temp.getMessage().substring(1,2).equals("L")) {
						temp.setMessage(temp.getMessage().toLowerCase());
					}
					tweetList.add(temp);
				}
				tweetchild.setValue(tweetList);
				adapter.notifyDataSetChanged();
			}
			public void onCancelled(FirebaseError firebaseError) {
			}
		});

		Button clearButton = (Button) findViewById(R.id.clear);
        clearButton.setOnClickListener(new View.OnClickListener() {
			/**
			 * This function enables the "clear" button for the application.
			 * When the button is pressed, this function will remove all
			 * tweets stored in the application and cause a display refresh.
			 *
			 * @param v
			 */
            public void onClick(View v) {
                setResult(RESULT_OK);

                tweetList.clear();
                tweetcounter = 0;
                adapter.notifyDataSetChanged();
                saveInFile();

            }
        });

	}

	/**
	 * Overrides the startup method for Android.
	 * Performs initialization of the application.
	 */
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		loadFromFile();
		adapter = new ArrayAdapter<Tweet>(this,
				R.layout.list_item, tweetList);
		oldTweetsList.setAdapter(adapter);
	}


	public ArrayList<Tweet> getTweets() {
		return tweetList; // Already added in chronological order!
	}

	public int getCount() {
		return tweetcounter;
	}

	public boolean hasTweet(Tweet tweet) {
		boolean duplicate = false;
		for (int i=0; i<tweetcounter;++i) {
			if (tweetList.get(i) == tweet) duplicate = true;
		}
		return duplicate;
	}

	public void addTweet(Tweet tweet) {
		if (hasTweet(tweet)) throw new IllegalArgumentException("Tweet Already Added!\n");
		else {
			tweetcounter += 1;
			tweetList.add(tweet);
			adapter.notifyDataSetChanged();
			saveInFile();
		}

	}

	/**
	 * This function is called on startup, and will load
	 * all tweets stored in JSON from persistent storage.
	 */
	private void loadFromFile() {
        try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));

			Gson gson = new GsonBuilder().create();
			Type listType = new TypeToken<ArrayList<Tweet>>(){}.getType();
			tweetList = gson.fromJson(in, listType);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function is called on any new input and will
	 * save all tweets to file in JSON so that they
	 * can be loaded later with data persistence.
	 */
	private void saveInFile() {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_PRIVATE);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

			Gson gson = new GsonBuilder().create();
			gson.toJson(tweetList,out);
            out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}