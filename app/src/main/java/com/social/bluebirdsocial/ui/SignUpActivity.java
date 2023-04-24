package com.social.bluebirdsocial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.social.bluebirdsocial.R;
import com.social.bluebirdsocial.databinding.ActivitySignUpBinding;
import com.social.bluebirdsocial.domain.entity.User;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    FirebaseAuth firebaseAuth;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.icBack.setOnClickListener(ic -> {
            onBackPressed();
        });
        binding.btnSignup.setOnClickListener(v -> {
            if(validateInput()){
                createUser();
            }
        });
    }

    private void createUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.layoutSignUp.setAlpha(0.2f);
        firebaseAuth.createUserWithEmailAndPassword(binding.email.getText().toString().trim(), binding.pass.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = new User(0,"","","",binding.email.getText().toString(),0,FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                    FirebaseAuth.getInstance().getCurrentUser().getUid(),binding.name.getText().toString());
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            reference.child(("users")).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                binding.progressBar.setVisibility(View.VISIBLE);
                                                finishAffinity();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(SignUpActivity.this, "Sai email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                            binding.progressBar.setVisibility(View.GONE);
                            binding.layoutSignUp.setAlpha(1f);
                        }
                    }
                });
    }

    private boolean validateInput() {
        String Name = binding.name.getText().toString().trim();
        String Email = binding.email.getText().toString().trim();
        String Pass = binding.pass.getText().toString().trim();
        String RePass = binding.rePass.getText().toString().trim();
        if(TextUtils.isEmpty(Name)){
            binding.name.setError("Vui lòng nhập Họ tên!", null);
            binding.name.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Email)) {
            binding.email.setError("Vui lòng nhập Email!", null);
            binding.email.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(Pass)) {
            binding.pass.setError("Vui lòng nhập mật khẩu!", null);
            binding.pass.requestFocus();
            return false;
        }else if (TextUtils.isEmpty(RePass)) {
            binding.rePass.setError("Vui lòng xác nhận mật khẩu!", null);
            binding.rePass.requestFocus();
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            binding.email.setError("sai định dạng email", null);
            binding.email.requestFocus();
            return false;
        }else if (!(RePass.matches(Pass)) || TextUtils.isEmpty(RePass)) {
            binding.rePass.setError("mật khẩu không trùng khớp", null);
            binding.rePass.requestFocus();
            return false;
        }else {
            return true;
        }
    }
}
