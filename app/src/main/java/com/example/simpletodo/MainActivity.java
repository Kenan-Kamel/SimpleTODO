package com.example.simpletodo;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList ;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    public static final String KEY_ITEM_TEXT = "item_text" ;
    public static final String KEY_ITEM_POSITION  = "item_position" ;
    public static final int EDIT_TEXT_CODE = 20 ;

    List<String> items;
    Button btnAdd ;
    EditText etitem ;
    RecyclerView rvitem ;

    ItemAdapter itemAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     btnAdd = findViewById(R.id.btnAdd);
     etitem = findViewById(R.id.etitem);
     rvitem = findViewById(R.id.rvitem);

      loadItems();


        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnLongClickListener() {

         @Override
         public void onItemLongClicked(int position) {
             items.remove(position);
             // Notify adapter about the remove
             itemAdapter.notifyItemRemoved(position);
             Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
             saveItems();

         }
     };
      ItemAdapter.OnClickListener onClickListener = new ItemAdapter.OnClickListener() {
          @Override
          public void onItemClicked(int position) {
              Log.d("MainActivity","Single click at position"+ position) ;
              // Create a new Activity
              Intent i = new Intent(MainActivity.this, EditActivity.class) ;
              // pass the data
              i.putExtra(KEY_ITEM_TEXT,items.get(position)) ;
              i.putExtra(KEY_ITEM_POSITION,position) ;
              // show the activity
              startActivityForResult(i,EDIT_TEXT_CODE);

          }
      };
        itemAdapter  = new ItemAdapter(items,onLongClickListener, onClickListener) ;
      rvitem.setAdapter(itemAdapter);
      rvitem.setLayoutManager(new LinearLayoutManager(this));
      btnAdd.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

           String todoitem = etitem.getText().toString() ;
           items.add(todoitem) ;
           itemAdapter.notifyItemInserted(items.size()-1);
           etitem.setText("");
           Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
           saveItems();

        }
    });

    }
   /// handle the result of edit activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE)
        {
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION) ;

            // update the module
            items.set(position,itemText);
            itemAdapter.notifyItemChanged(position);
            saveItems();
            Toast.makeText(getApplicationContext(),"Item updated",Toast.LENGTH_SHORT).show();


        }else {
            Log.w("MainActivity","Unknown Call to onActivityResult");
        }
    }

    private File getDataFile () {

        return new File(getFilesDir(), "data.txt") ;

    }

    // function to read the data of the file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items ", e ) ;
            items = new ArrayList<>() ;


        }


    }
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile() ,items) ;
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items ", e ) ;
        }
    }
}