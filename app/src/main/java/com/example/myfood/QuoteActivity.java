package com.example.myfood;


import android.os.Bundle;

import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuoteActivity extends AppCompatActivity {

    private TextView txtQuote;
    private TextView txtAuthor;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quote);

        //setting up the application
        setUpView();

        txtAuthor.setText(ChooseMealActivity.quoteAuthor);
        txtQuote.setText(ChooseMealActivity.quoteText);

        //event listeners
    }

    public void setUpView(){
        txtQuote = findViewById(R.id.txtQuote);
        txtAuthor = findViewById(R.id.txtAuthor);
        scroll = findViewById(R.id.scroll);
    }
}
