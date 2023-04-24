//package com.example.assingment_coffee;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//public class signup extends AppCompatActivity {
//
//    private boolean otpSent = false;
//    private String countryCode = "+855";
//    private String id="";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        final EditText phonenumber = findViewById(R.id.phone_number);
//        final EditText verify = findViewById(R.id.verify);
//        final Button btn_getcode = findViewById(R.id.for_signup);
//
//        FirebaseApp.initializeApp(this);
//
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//
//        btn_getcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(otpSent){
//                    final String getOTP = verify.getText().toString();
//
//                    if(id.isEmpty()){
//                        Toast.makeText(signup.this,"Unable to verify OTP", Toast.LENGTH_SHORT).show();
//                    }else{
//                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(id,getOTP);
//                        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    FirebaseUser userDetails = task.getResult().getUser();
//
//                                    Toast.makeText(signup.this,"Verified",Toast.LENGTH_SHORT).show();
//                                }else{
//                                    Toast.makeText(signup.this,"Something wnt wrong!!!",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                    }
//                }else{
//                    final String get_phone = phonenumber.getText().toString();
//
//                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(firebaseAuth)
//                            .setPhoneNumber(countryCode+""+get_phone)
//                            .setTimeout(60L, TimeUnit.SECONDS)
//                            .setActivity(signup.this)
//                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                @Override
//                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                    Toast.makeText(signup.this,"OTP send successfully",Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onVerificationFailed(@NonNull FirebaseException e) {
//                                    Toast.makeText(signup.this,"Something went wrong"+e.getMessage(),Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                    super.onCodeSent(s, forceResendingToken);
//
//                                    btn_getcode.setText("Sign Up");
//                                    id = s;
//                                    otpSent = true;
//                                }
//                            }).build();
//                    PhoneAuthProvider.verifyPhoneNumber(options);
//                }
//            }
//        });
//    }
//}


package com.example.assingment_coffee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    EditText signup_username,signup_password,signup_email,signup_phonenumber;
    Button signup_button;
    LoginDB DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_username = (EditText) findViewById(R.id.signup_username);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_phonenumber = (EditText) findViewById(R.id.signup_phonenumber);
        signup_button = (Button) findViewById(R.id.signup_button);
        DB = new LoginDB(this);

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = signup_username.getText().toString();
                String password = signup_password.getText().toString();
                String email = signup_email.getText().toString();
                String phonenumber = signup_phonenumber.getText().toString();

                if (username.equals("") || password.equals("") || email.equals("") || phonenumber.equals(""))
                    Toast.makeText(signup.this, "Please enter all this fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuser = DB.checkusername(username);
                    if(checkuser == false){
                        Boolean insert = DB.insertData(username,password,email,phonenumber);
                        if(insert == true){
                            Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(signup.this, "Registered Failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(signup.this, "User already exists!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}