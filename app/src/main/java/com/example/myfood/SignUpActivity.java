package com.example.myfood;


import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {

    private TextView txtTitle;
    private TextView txtLoginToAccount;
    private TextView txtUsername;
    private TextView txtPassword;
    private EditText tfUsername;
    private EditText tfPassword;
    private Button btnSignUpAccount;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //setting up the application
        setUpView();

        //event listeners
        btnSignUpAccount.setOnClickListener(v -> {
            String username = tfUsername.getText().toString();
            String password = tfPassword.getText().toString();
            if(!username.isEmpty() && !password.isEmpty()){
                User user = new User(username,password);
                SignInActivity.users.add(user);
                String usersJSON = gson.toJson(SignInActivity.users);
                SignInActivity.editor.putString("users", usersJSON);
                SignInActivity.editor.commit();
                finish();
            }
        });
    }

    public void setUpView(){
        txtTitle = findViewById(R.id.txtTitle);
        txtLoginToAccount = findViewById(R.id.txtLoginToAccount);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        tfUsername = findViewById(R.id.tfUsername);
        tfPassword = findViewById(R.id.tfPassword);
        btnSignUpAccount = findViewById(R.id.btnSignUpAccount);
    }
}
