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

import static android.app.ProgressDialog.show;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        final TextView searchBar = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new OnClickListener() {

            public void onClick(View view) {
                //Toast.makeText(MyActivity.this, searchBar.getText().toString(), Toast.LENGTH_SHORT).show();
                String query = searchBar.getText().toString();
                lookUp(query);

            }
        });
    }

    public void lookUp(String query) {

        //Toast.makeText(MyActivity.this, "reached lookup function", Toast.LENGTH_SHORT).show();

        Cursor c = getContentResolver().query(Uri.parse("content://sms/inbox"),null,null,null, null);
        startManagingCursor(c);
        

        int smsEntriesCount = c.getCount();

        String[] body = new String[smsEntriesCount]; //might need to change this to allocate more space for sent + received
        String[] number = new String[smsEntriesCount];
        String[] id = new String[smsEntriesCount];

        if (c.moveToFirst())
        {
            for (int i = 0; i < smsEntriesCount; i++)
            {
                body[i] = c.getString(c.getColumnIndexOrThrow("body")).toString();
                number[i] = c.getString(c.getColumnIndexOrThrow("address")).toString();
                id[i] = c.getString(c.getColumnIndexOrThrow("_id")).toString();
                c.moveToNext();
            }
        }
        Toast.makeText(MyActivity.this, id[2], Toast.LENGTH_SHORT).show();
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
