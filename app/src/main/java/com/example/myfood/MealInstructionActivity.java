package com.example.myfood;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MealInstructionActivity extends AppCompatActivity {

    private TextView txtMakeMeal;
    private ScrollView scrollBar;
    private TextView txtInstruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_instructions);

        //setting up the application
        setUpView();
        txtInstruction.setText(ChooseMealActivity.mealInstructions);
        //event listeners
    }

    public void setUpView(){
        txtMakeMeal = findViewById(R.id.txtMakeMeal);
        scrollBar = findViewById(R.id.scrollBar);
        txtInstruction = findViewById(R.id.txtInstruction);
    }
}
