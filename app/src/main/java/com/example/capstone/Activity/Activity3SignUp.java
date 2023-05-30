package com.example.capstone.Activity; // completed

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.capstone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity3SignUp extends AppCompatActivity {
    private EditText edtSignUpEmail, edtSignUpPwd, edtSignAge, edtSignName;
    private ImageView imgv, btnSign;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_signup);
        edtSignUpEmail = (EditText) findViewById(R.id.edtSinMail);
        edtSignUpPwd = (EditText) findViewById(R.id.edtSinPwd);
        edtSignAge = (EditText) findViewById(R.id.edtSinAge);
        edtSignName = (EditText) findViewById(R.id.edtSinName);
        btnSign = findViewById(R.id.btnSign);
        imgv = findViewById(R.id.imageView);
        firebaseAuth = FirebaseAuth.getInstance();
        final Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.animationfortext2);
        imgv.startAnimation(animation);
        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtSignUpEmail.getText().toString().trim();
                final String pwd = edtSignUpPwd.getText().toString().trim();
                final String age = edtSignAge.getText().toString().trim();
                final String name = edtSignName.getText().toString().trim();
                firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(Activity3SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent mIntent = new Intent(Activity3SignUp.this, Activity4Certified.class);
                            mIntent.putExtra("usrId", email);
                            startActivity(mIntent);
                            finish();
                        } else if (edtSignUpEmail.getText().toString() == null) {
                            Toast.makeText(Activity3SignUp.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(Activity3SignUp.this, "등록할 수 없습니다.", Toast.LENGTH_SHORT).show();
//                            return;
                        }
                    }
                });
            }
        });
    }
}