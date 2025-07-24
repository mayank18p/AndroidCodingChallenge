package com.example.androidcodingchallenge.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidcodingchallenge.databinding.LoginActivityBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.viewmodel.MainViewModel;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private LoginActivityBinding binding;
    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        binding = LoginActivityBinding.inflate(getLayoutInflater());

        UserRepository repository = new UserRepository();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(MainViewModel.class);

        setContentView(binding.getRoot());

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(binding.emailInput.getText());
                if (email.isEmpty()) {
                    binding.emailInput.setError("Email is required");
                    return;
                }

            }
        });

    }
}
