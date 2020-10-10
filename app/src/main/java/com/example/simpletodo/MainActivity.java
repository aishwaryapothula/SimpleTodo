package com.example.simpletodo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Creating a model with name items
    List<String> items;

    // Reference of each view inorder to get a handler on each of them
    // and write appropriate logic
    // Can have any name here, just defining a Button type
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Defining member variables, connecting to the actual view
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);


        // Instantiating model to be a list
        loadItems();
//        items.add("Email professor");
//        items.add("Submit Application");
//        items.add("Finish project");
//        items.add("Play tennis");

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return false;
            }

            @Override
            public void onItemLongClicked(int position) {
                // Delete item from the model
                items.remove(position);
                // Notify the adapter
                itemsAdapter.notifyItemRemoved(position);
                saveItems();
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onLongClick(View v, int position) {
                return false;
            }
        };

        itemsAdapter = new ItemsAdapter(items,onLongClickListener );
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
//
//
//        // In order to add to the list when user types an item
//        // Listening for user actions
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
               String todoItem =  etItem.getText().toString();
               // Add item to the model
                items.add(todoItem);
               // notify adaptor that an item is inserted
                itemsAdapter.notifyItemInserted(items.size() -1);
                etItem.setText("");
                saveItems();
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // For persistence
    // It will return the file in which we will store the to do items
    private File getDataFile(){
        // Directory and name of the file are returned
        return new File(getFilesDir(), "data.txt");
    }

    // The function will read the datafile - load items by reading every line of data.txt
    // Load items will be called only once when the app loads
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    // For writing the file
    // Save items will be called whenever items are added
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }
    }

}