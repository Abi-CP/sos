package com.example.sos;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sos.AccountActivity;
import com.example.sos.LoginActivity;
import com.example.sos.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextName, editTextPassword, editTextConfirmPassword;
    private Button buttonSave;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextName = findViewById(com.example.sos.R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSave = findViewById(R.id.buttonSave);
        TextView logInTxt = findViewById(R.id.logInTxt);

        mAuth = FirebaseAuth.getInstance();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextName.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUpActivity.this, "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignUpActivity.this, "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                buttonSave.setEnabled(false);
                buttonSave.setText("Verifying...");

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignUpActivity.this, "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                    buttonSave.setEnabled(true);
                                                    buttonSave.setText("Sign Up");

                                                    buttonSave.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            if (user.isEmailVerified()) {
                                                                Intent intent = new Intent(SignUpActivity.this, AccountActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                Toast.makeText(SignUpActivity.this, "Please verify your email first!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                                    buttonSave.setEnabled(true);
                                                    buttonSave.setText("Verify Email");
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                                    buttonSave.setEnabled(true);
                                    buttonSave.setText("Verify Email");
                                }
                            }
                        });
            }
        });

        logInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
