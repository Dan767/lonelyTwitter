package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
	private ArrayAdapter<Tweet> adapter;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

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
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				Tweet tweet = new Tweet(text);

				tweetList.add(tweet);
				//saveInFile(text, new Date(System.currentTimeMillis()));
				//finish();

                adapter.notifyDataSetChanged();
                saveInFile();

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
                //saveInFile(text, new Date(System.currentTimeMillis()));
                //finish();

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

	/**
	 * This function is called on startup, and will load
	 * all tweets stored in JSON from persistent storage.
	 */
	private void loadFromFile() {
		//ArrayList<String> tweets = new ArrayList<String>();


        try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));

			Gson gson = new GsonBuilder().create();


			Type listType = new TypeToken<ArrayList<Tweet>>(){}.getType();

			tweetList = gson.fromJson(in, listType);
			/*

			String line = in.readLine();
			while (line != null) {
				tweets.add(line);
				line = in.readLine();
			}
			*/

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return tweets.toArray(new String[tweets.size()]);
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

			//FileWriter out = new FileWriter(FILENAME);

			Gson gson = new GsonBuilder().create();

			gson.toJson(tweetList,out);
			//fos.write(new String(date.toString() + " | " + text)
			//		.getBytes());
			//fos.close();
            out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}