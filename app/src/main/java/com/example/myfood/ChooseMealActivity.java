package com.example.myfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Random;

public class ChooseMealActivity extends AppCompatActivity {

    private TextView txtTitle;
    private ImageView imageViewHero;
    private TextView txtChooseMeal;
    private Spinner mealsSpinner;
    private Button btnMakeMeal;

    private Button btnQuote;

    static String quoteText;
    static String quoteAuthor;

    static String mealInstructions;

    private RequestQueue queue;
    private JSONArray mealsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_meal);

        //setting up the application
        setUpView();

        queue = Volley.newRequestQueue(this);
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        mealsArray = response.getJSONArray("meals");
                        ArrayList<String> mealsList = new ArrayList<>();

                        for (int i = 0; i < 20; i++) {
                            JSONObject meal = mealsArray.getJSONObject(i);
                            String mealName = meal.getString("strMeal");
                            mealsList.add(mealName);
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(ChooseMealActivity.this, android.R.layout.simple_spinner_item, mealsList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        // Set the adapter to the spinner
                        mealsSpinner.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.d("volley_error", error.toString())
        );

        queue.add(request);

        String quoteUrl = "https://type.fit/api/quotes";
        JsonArrayRequest quoteRequest = new JsonArrayRequest(Request.Method.GET, quoteUrl, null,
                response -> {
                    try {
                        int randomIndex = new Random().nextInt(16);
                        JSONObject quoteObject = response.getJSONObject(randomIndex);
                        quoteText = quoteObject.getString("text");
                        quoteAuthor = quoteObject.getString("author");
                        quoteAuthor = quoteAuthor.substring(0,quoteAuthor.indexOf(","));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle error
                });

        queue.add(quoteRequest);


        btnMakeMeal.setOnClickListener(v -> {
            try {
                mealInstructions = getInstruction(mealsSpinner.getSelectedItem().toString());
                Intent intent = new Intent(ChooseMealActivity.this, MealInstructionActivity.class);
                startActivity(intent);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });

        btnQuote.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseMealActivity.this, QuoteActivity.class);
            startActivity(intent);
        });

    }

    public void setUpView(){
        txtTitle = findViewById(R.id.txtTitle);
        imageViewHero = findViewById(R.id.imageViewHero);
        txtChooseMeal = findViewById(R.id.txtChooseMeal);
        mealsSpinner = findViewById(R.id.mealsSpinner);
        btnMakeMeal = findViewById(R.id.btnMakeMeal);
        btnQuote = findViewById(R.id.btnQuote);
    }

    public String getInstruction(String mealName) throws JSONException {
        for(int i = 0;i<mealsArray.length();i++){
            JSONObject meal = mealsArray.getJSONObject(i);
            if(meal.getString("strMeal").equals(mealName)){
                return meal.getString("strInstructions");
            }
        }
        return "";
    }
}
