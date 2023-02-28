package com.example.advanced;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextView signup_text_view;
    EditText email_edit_text,password_edit_text;
    Button login_button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup_text_view=findViewById(R.id.signuptext);
        email_edit_text=findViewById(R.id.emailedittext);
        password_edit_text=findViewById(R.id.passwordedittext);
        login_button=findViewById(R.id.loginbtn);

        signup_text_view.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,SignupActivity.class)));
        login_button.setOnClickListener(v -> logintoacc());
    }
     void logintoacc(){
        String email=email_edit_text.getText().toString();
        String password=password_edit_text.getText().toString();

        boolean dataverified=checkinserteddata(email,password);
        if(!dataverified){
            return;
        }
        loginviafirebase(email,password);
    }

     void loginviafirebase(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(firebaseAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "login successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "invalid email or password", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(LoginActivity.this, "well well well", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    boolean checkinserteddata(String email,String pwd){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit_text.setError("please enter a valid email");
            return false;
        } else if (pwd.length()<8) {
            password_edit_text.setError("password is too short");
            return false;
        }
            return true;

    }

}