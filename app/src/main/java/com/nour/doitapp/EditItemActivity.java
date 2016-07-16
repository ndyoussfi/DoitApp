package com.nour.doitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText mlEditText;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        mlEditText = (EditText) findViewById(R.id.editTxt);
        String itemEdited = getIntent().getStringExtra("item");
        System.out.println("im hereeeeeeeeeee");
        position = getIntent().getExtras().getInt("position");
        mlEditText.setText(itemEdited);
        mlEditText.setSelection(mlEditText.getText().length());
    }
    public void onSave(View v) {
        String etstr = mlEditText.getText().toString();
        Intent data = new Intent();
        data.putExtra("item", etstr);
        data.putExtra("code", 20);
        data.putExtra("newPosition", position);
//        data.putExtra("position", data.getExtras().getInt("position"));
        // closes the activity and returns to first screen
        setResult(RESULT_OK, data);
        this.finish();
    }
}
