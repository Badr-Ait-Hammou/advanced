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

public class SignupActivity extends AppCompatActivity {

    EditText email_edit_text,password_edit_text,confirmpassword_edit_text;
    TextView login_text_view;
    Button signup_button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email_edit_text=findViewById(R.id.emailedittext);
        password_edit_text=findViewById(R.id.passwordedittext);
        confirmpassword_edit_text=findViewById(R.id.confirmpasswordedittext);
        login_text_view=findViewById(R.id.logintextview);
        signup_button=findViewById(R.id.signupbtn);
        login_text_view.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this,LoginActivity.class)));
        signup_button.setOnClickListener(v -> addaccount());



    }
    public void addaccount(){
        String email=email_edit_text.getText().toString();
        String pwd=password_edit_text.getText().toString();
        String confirmpwd=confirmpassword_edit_text.getText().toString();

        boolean datachecked=checkinserteddata(email,pwd,confirmpwd);
        if(!datachecked){
            return;
        }
        cretaeaccinfb(email,pwd);
    }

    void cretaeaccinfb(String email,String password){
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "account created successfully,check your email", Toast.LENGTH_SHORT).show();
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    boolean checkinserteddata(String email,String password,String confpassword){
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_edit_text.setError("invalid email address");
            return false;
        }else if(password.length()<8){
            password_edit_text.setError("password too short");
            return false;
        }else if(!password.equals(confpassword)){
            confirmpassword_edit_text.setError("password doesn't match");
            return false;

        }else
             return true;
    }
}