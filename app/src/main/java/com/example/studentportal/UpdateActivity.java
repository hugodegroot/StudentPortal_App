package com.example.studentportal;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class UpdateActivity extends AppCompatActivity {

    private EditText url;
    private EditText title;

    private Portal portal;

    Button addButton;

    public static final String EXTRA_PORTAL = "Portal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        url = findViewById(R.id.urlInput);
        title = findViewById(R.id.titleInput);

        addButton = findViewById(R.id.addPortal);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                portal = new Portal(title.getText().toString(), url.getText().toString());
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                intent.putExtra("Portal", portal);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        });


    }
}
