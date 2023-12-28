package com.example.myfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {

    //setting up the ui
    private TextView txtTitle;
    private TextView txtLoginToAccount;
    private TextView txtUsername;
    private TextView txtPassword;
    private EditText tfUsername;
    private EditText tfPassword;
    private Button btnSignIn;
    private TextView txtDontHaveAccount;
    private Button btnSignUp;
    private TextView txtNotRegistered;

    //setting up the database of the application
    static SharedPreferences preference;
    static SharedPreferences.Editor editor;
    Gson gson = new Gson();

    //to store the signed users in our database
    static ArrayList<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        //setting up the application
        setUpView();
        setupSharedPreference();
        setupUsersDatabase();

        //event listeners
        btnSignIn.setOnClickListener(v -> {
            String username = tfUsername.getText().toString();
            String password = tfPassword.getText().toString();
            if(!username.isEmpty() && !password.isEmpty()){
                User user = getUser(username,password);
                if(user != null){
                    Intent intent = new Intent(SignInActivity.this, ChooseMealActivity.class);
                    startActivity(intent);
                }else{
                    txtNotRegistered.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public void setUpView(){
        txtTitle = findViewById(R.id.txtTitle);
        txtLoginToAccount = findViewById(R.id.txtLoginToAccount);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        txtDontHaveAccount = findViewById(R.id.txtDontHaveAccount);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtNotRegistered = findViewById(R.id.txtNotRegistered);

        User user = new User("mot","123");
        users.add(user);
    }

    public void setupSharedPreference(){
        preference = getSharedPreferences("database",MODE_PRIVATE);
        editor = preference.edit();
    }

    public void setupUsersDatabase(){
        String usersJSON = gson.toJson(users);
        editor.putString("users",usersJSON);
        editor.commit();
    }

    public User getUser(String username, String password){
        String usersJson = preference.getString("users", "");
        Type userListType = new TypeToken<ArrayList<User>>() {}.getType();
        ArrayList<User> usersList = gson.fromJson(usersJson, userListType);
        for(User user : usersList){
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}