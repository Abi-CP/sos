package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button logInBtn = findViewById(R.id.buttonSave);
        EditText password = findViewById(R.id.editTextPassword);
        userName  = findViewById(R.id.editTextName);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        TextView signUp = findViewById(R.id.signUpTxt);

        logInBtn.setOnClickListener(v -> {
            if(userName.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                Toast.makeText(this, "Please Enter valid value", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(userName.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(task -> {
                        Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AccountActivity.class);
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show());
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setIntent(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(userName.getText().toString());
            }
        });


    }
}