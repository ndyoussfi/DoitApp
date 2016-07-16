package com.nour.doitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aTodoAdapter;
    ListView lvItems;
    EditText etEditText;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.listView_items);
        lvItems.setAdapter(aTodoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                todoItems.remove(i);
                aTodoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                launchEditView(i);
                System.out.println("position is "+ i);
            }
        });
    }
    public void populateArrayItems(){
        todoItems = new ArrayList<String>();
//        todoItems.add("item 1");
//        todoItems.add("item 2");
//        todoItems.add("item 3");
        readItems();
        aTodoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

    }
    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch (IOException e){

        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(file, todoItems);
        }catch (IOException e){

        }
    }

    public void onAddItem(View view) {
        String itemText = etEditText.getText().toString();
        todoItems.add(itemText);
        etEditText.setText("");
        writeItems();
    }

    public void launchEditView(int position) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("item", todoItems.get(position).toString());
        i.putExtra("position", position);
        startActivityForResult(i, REQUEST_CODE); // brings up the second activity
    }

    // ActivityOne.java, time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            int position = data.getExtras().getInt("newPosition");
            System.out.println("saved position is "+ data.getExtras().getInt("position"));
            System.out.println("local position is "+ position);
            String str = data.getExtras().getString("item");
            todoItems.set(position, str);
            aTodoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
