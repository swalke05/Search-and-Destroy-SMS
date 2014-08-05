package com.example.Search_and_Destroy_SMS;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.SearchView;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import static android.app.ProgressDialog.show;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        final EditText searchBar = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                //Toast.makeText(MyActivity.this, searchBar.getText().toString(), Toast.LENGTH_SHORT).show();
                String query = searchBar.getText().toString();
                lookUp(query);

            }
        });
    }

    public void lookUp(String query) {

        //Toast.makeText(MyActivity.this, query, Toast.LENGTH_SHORT).show();

        Cursor c = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null,null);
        startManagingCursor(c);

        int j = 0;
        int smsEntriesCount = c.getCount();

        ListView myListView = (ListView) findViewById( R.id.myListView ); //ListView for displaying matched text messages
        ArrayAdapter<SMS> listAdapter;
        final ArrayList<SMS> messages = new ArrayList<SMS>(); //ArrayList for holding all matched text messages MIGHT NOT WANT FINAL

        String currentMessage; //Temporary string for current message body

        //If the text messages folder is not empty
        //Loop through all messages
        if (c.moveToFirst()) {
            for (int i = 0; i < smsEntriesCount; i++) {
                //Get the body of the message we are looking at
                currentMessage = c.getString(c.getColumnIndexOrThrow("body")).toString();

                //If the message body contains the substring
                if (currentMessage.toLowerCase().contains(query.toLowerCase())) {

                    SMS currentText = new SMS(null, null, null); //Create new SMS object


                    //Save the message, SMS id and phone number in the SMS object
                    currentText.body = c.getString(c.getColumnIndexOrThrow("body")).toString();
                    currentText.number = c.getString(c.getColumnIndexOrThrow("address")).toString();
                    currentText.id = c.getString(c.getColumnIndexOrThrow("_id")).toString();

                    //Add this SMS to the Arraylist
                    messages.add(currentText);

                    //Toast.makeText(MyActivity.this, currentText.body, Toast.LENGTH_SHORT).show(); //testing
                }

                c.moveToNext();
            }
        }

        //Reposition cursor to search through sent messages
        c = getContentResolver().query(Uri.parse("content://sms/sent"),null,null,null,null);
        startManagingCursor(c);
        smsEntriesCount = c.getCount(); //Get number of sent messages

        if (c.moveToFirst()) {
            for (int i = 0; i < smsEntriesCount; i++) {
                //Get the body of the message we are looking at
                currentMessage = c.getString(c.getColumnIndexOrThrow("body")).toString();

                //If the message body contains the substring
                if (currentMessage.toLowerCase().contains(query.toLowerCase())) {

                    SMS currentText = new SMS(null, null, null); //Create new SMS object


                    //Save the message, SMS id and phone number in the SMS object
                    currentText.body = c.getString(c.getColumnIndexOrThrow("body")).toString();
                    currentText.number = c.getString(c.getColumnIndexOrThrow("address")).toString();
                    currentText.id = c.getString(c.getColumnIndexOrThrow("_id")).toString();

                    //Add this SMS to the Arraylist
                    messages.add(currentText);

                    //Toast.makeText(MyActivity.this, currentText.body, Toast.LENGTH_SHORT).show(); //testing
                }

                c.moveToNext();
            }
        }



        listAdapter = new ArrayAdapter<SMS>(this, R.layout.main2, messages);
        myListView.setAdapter(listAdapter);

        myListView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                    int position, long arg3) {
                 SMS  message = (SMS) adapterView
                        .getItemAtPosition(position);
                String testmessage = message.body;
                //from the content object retrieve the attributes you require.
                Toast.makeText(MyActivity.this, testmessage, Toast.LENGTH_SHORT).show();
            }

        });



        //Toast.makeText(MyActivity.this, id[2], Toast.LENGTH_SHORT).show();
        c.close();
    }

    public boolean deleteSms(String smsId) {
        boolean isSmsDeleted = false;
        try {
            getContentResolver().delete(
                    Uri.parse("content://sms/" + smsId), null, null);
            isSmsDeleted = true;

        } catch (Exception ex) {
            isSmsDeleted = false;
        }
        return isSmsDeleted;
    }
}

