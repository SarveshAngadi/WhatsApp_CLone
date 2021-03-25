package com.example.whatsapp_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumberActivity extends AppCompatActivity {

    Button getotpbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        final EditText enternumber = findViewById(R.id.input_phone_number);
        getotpbutton = findViewById(R.id.button_get_otp);

        final ProgressBar progressBar = findViewById(R.id.progressbar_verify_otp);

        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(enternumber.getText().toString().isEmpty()) {
                    Toast.makeText(PhoneNumberActivity.this, "Enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
//                }else if(enternumber.getText().toString().length()<10&&enternumber.getText().toString().length()>10){
//                    Toast.makeText(PhoneNumberActivity.this, "Enter a valid phone number!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                progressBar.setVisibility(View.VISIBLE);
                getotpbutton.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + enternumber.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            PhoneNumberActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    getotpbutton.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    getotpbutton.setVisibility(View.VISIBLE);
                                    Toast.makeText(PhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.GONE);
                                    getotpbutton.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                    intent.putExtra("mobile", enternumber.getText().toString());
                                    intent.putExtra("backendotp", backendotp);
                                    startActivity(intent);

                                }
                            }
                    );
                }
        });
    }
}
