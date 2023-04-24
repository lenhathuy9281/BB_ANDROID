package com.social.bluebirdsocial.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.social.bluebirdsocial.R;
import com.social.bluebirdsocial.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cavBack.setOnClickListener(cav ->{
            onBackPressed();
        });

        binding.btnForgotPass.setOnClickListener(btn ->{
            if(validateEmail()){
                sendEmail();
            }
        });
    }

    private void sendEmail(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String email = binding.edEmailForgot.getText().toString();
        firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ForgotPasswordActivity.this, "Đã gửi! Hãy kiểm tra email của bạn và đăng nhập lại!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Không gửi được! Hãy kiểm tra lại email!", Toast.LENGTH_SHORT).show();
                        binding.layoutForgot.setAlpha(1f);
                    }
                });


    }


    private boolean validateEmail(){
        String strEmail = binding.edEmailForgot.getText().toString().trim();

        if(TextUtils.isEmpty(strEmail)){
            binding.edEmailForgot.setError("Hãy nhập email của bạn!",null);
            binding.edEmailForgot.requestFocus();
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            binding.edEmailForgot.setError("Nhập emai đúng định dạng \'abc@gmail.com\'.",null);
            binding.edEmailForgot.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}
