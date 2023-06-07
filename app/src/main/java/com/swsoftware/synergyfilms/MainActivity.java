package com.swsoftware.synergyfilms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Oscar films list");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        try {
            InputStream jsonData = getAssets().open("films.json");
            BufferedReader jsonReader = new BufferedReader(new InputStreamReader(jsonData));
            String inputString;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputString = jsonReader.readLine()) != null) {
                stringBuilder.append(inputString);
            }
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            FilmsAdapter adapter = new FilmsAdapter(this, jsonArray);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}