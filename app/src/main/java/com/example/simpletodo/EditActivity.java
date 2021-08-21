package com.example.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    EditText etItem ;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        etItem = findViewById(R.id.etItem);
        btnSave = findViewById(R.id.btnSave) ;

        getSupportActionBar().setTitle("Edit Item");

        getIntent().getStringExtra(MainActivity.KEY_ITEM_TEXT) ;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create an intent to return the result
                Intent intent  = new Intent() ;
                // pass the result
                intent.putExtra(MainActivity.KEY_ITEM_TEXT,etItem.getText().toString()) ;
                intent.putExtra(MainActivity.KEY_ITEM_POSITION,getIntent().getExtras().getInt(MainActivity.KEY_ITEM_POSITION));

                // set the result to the intent
                setResult(RESULT_OK,intent);

                // end the activity
                finish();

            }
        });


    }
}