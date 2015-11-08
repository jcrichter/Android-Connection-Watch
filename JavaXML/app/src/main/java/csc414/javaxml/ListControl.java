package csc414.javaxml;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by coltonladner on 11/8/15.
 */
public class ListControl extends ListActivity {

    ArrayList<String> listItems = new ArrayList<String>();

    ArrayAdapter<String> adapter;

    int clickCounter = 2;

    ListView list;

    @Override
    public void onCreate(Bundle icicle) {

        list = (ListView) findViewById(R.id.listView);
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, listItems);
        list.setAdapter(adapter);

    }

    public void addItems(View v){

        clickCounter++;
        listItems.add("Clicked "+clickCounter);
        adapter.notifyDataSetChanged();

    }

    public void reset(View v){

        listItems.clear();
        clickCounter = 0;
        listItems.add("Click "+clickCounter);
        adapter.notifyDataSetChanged();

    }

}
