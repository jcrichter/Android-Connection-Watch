package csc414.androidconnectionwatchdog;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
//TODO
//Import our databases into our main java file (this one)

public class WatchDogMain extends AppCompatActivity {

    static ArrayList<String> arrayList;
    static Button btn;
    static ListView list;
    static ArrayAdapter<String> adapter;
    static AppList aList;
    static int Threashold = 150;

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

        mContext = getApplicationContext(); //returned by getContext() don't delete this
        aList = new AppList();
        //btn = (Button) findViewById(R.id.button);       //Get the first button in xml.
        list = (ListView) findViewById(R.id.listView);  //Get the first listview in xml.
        list.setBackgroundColor(Color.BLACK);

        arrayList = new ArrayList<String>();            //Setup ArrayList of string to hold the service names.
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList); //May have to change this layout to suit the gui.
        list.setAdapter(adapter); //Attach adapter to list view

        this.writeProcessesToGUI();
        this.writeServicesToGUI();

        /*btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                WatchDogMain.clearGUI();
                WatchDogMain.writeProcessesToGUI();
                WatchDogMain.writeServicesToGUI();

            }
        });
        */
        /*
        //testing AppList functions

        //context objects seem to only not be null when made in the onCreate function. can't get around this

        mContext = getApplicationContext();
        AppList listOApps = new AppList();
        int numP = listOApps.getSizeProc();
        int numS = listOApps.getSizeServ();

        listOApps.updateLists();
        numS = listOApps.getSizeServ();
        numP = listOApps.getSizeProc();

        */
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public static void writeServicesToGUI(){

        aList.updateLists();

        int snum = aList.getSizeServ();
        int lcv = 0;

        arrayList.add("Services: " + aList.getSizeServ());      //Service header and amount.

        while(lcv < snum){      //Cycle through the service elements and add the short service name to the list.

            arrayList.add(aList.getServElement(lcv).service.toShortString());
            lcv++;

        }

        adapter.notifyDataSetChanged();     //Notify the adapter that the list has changed so it can update the gui.

    }

    public static void writeProcessesToGUI(){

        aList.updateLists();

        int pnum = aList.getSizeProc();
        int lcv = 0;

        arrayList.add("Processes: " + aList.getSizeProc());     //Process header and amount.

        while(lcv < pnum){          //Cycle through the process elements and add the process name to the list.

            ConnectionFinder conTest = new ConnectionFinder(aList.getProcElement(lcv).pid);
            String ipResult = conTest.getConnection();
            arrayList.add("PID : " + aList.getProcElement(lcv).pid + "\nName : " + aList.getProcElement(lcv).processName + "\nIP : " + ipResult);
            lcv++;

        }

        adapter.notifyDataSetChanged();     //Notify the adapter that the list has changed so it can update the gui.

    }

    public static void clearGUI(){

        arrayList.clear();
        adapter.notifyDataSetChanged();

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



