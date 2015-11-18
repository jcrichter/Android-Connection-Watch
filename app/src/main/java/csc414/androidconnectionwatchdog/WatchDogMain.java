package csc414.androidconnectionwatchdog;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
//TODO
//Import our databases into our main java file (this one)

public class WatchDogMain extends AppCompatActivity {
    //testing AppList
    private static Context mContext;
    private static WatchDogMain instance;
    public static WatchDogMain getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    //end test AppList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_dog_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //testing AppList functions

        /*
        mContext = getApplicationContext();
        BaileyAppList bList = new BaileyAppList(mContext); //give it a context thing
        String stuff = bList.firstName();
        String oth = bList.firstName();
        */

        //context objects seem to only not be null when made in the onCreate function. can't get around this
        mContext = getApplicationContext();
        AppList listOApps = new AppList(mContext);
        int numP = listOApps.getSizeProc();
        int numS = listOApps.getSizeServ();

        //end AppList Test

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_watch_dog_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //Handler for adding a new verified connection
            case R.id.action_add_verified_connection:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add a verified connection");
                //builder.setMessage("What connection do you want whitelisted?");
                final EditText inputField = new EditText(this);
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Here we will grab the user connection input from the verified connection alert
                        //We could catch valid connection input from the user with a regular expression
                        //As well as matching them to connections that we have found and stored in a database
                        String verifiedConnection = inputField.getText().toString();
                        //Log.d("WatchDogMain", inputField.getText().toString());
                        ConnectionDBHelper helper = new ConnectionDBHelper(WatchDogMain.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        //Add the verifiedConnection to the CONNECTION column of our database.
                        values.clear();
                        values.put(ConnectionContract.Columns.CONNECTION, verifiedConnection);

                        db.insertWithOnConflict(ConnectionContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                    }
                });

                builder.setNegativeButton("Cancel", null);

                builder.create().show();
                return true;

            default:
                return false;
        }
       // return super.onOptionsItemSelected(item);
    }
    //TODO
    //We need to figure out a way to use Android SQLite to store white-listed connections
    //H
   // db -> ConnectionContract.java
}



