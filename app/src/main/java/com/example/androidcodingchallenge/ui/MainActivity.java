package com.example.androidcodingchallenge.ui;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidcodingchallenge.R;
import com.example.androidcodingchallenge.databinding.MainActivityBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.utils.FragmentHelper;
import com.example.androidcodingchallenge.viewmodel.SharedViewModel;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private MainActivityBinding binding;
    private FragmentHelper fragmentHelper;
    private SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            fragmentHelper = new FragmentHelper(getSupportFragmentManager(), R.id.fragment_container);
            fragmentHelper.addFragment(new LoginFragment());
        }

        setData();
        setupObservers();
    }

    private void setData() {
        UserRepository repository = new UserRepository();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(SharedViewModel.class);
    }

    private void setupObservers() {
        viewModel.getEventData().observe(this, message -> {
            // Handle event
            switch (message) {
                case "Otp Fragment":
                    fragmentHelper.replaceFragment(new OtpFragment(), true);
                    break;
                case "Profile Fragment":
                    fragmentHelper.replaceFragment(new CompleteProfileFragment(), true);
                    break;
                case "Welcome Fragment":
                    fragmentHelper.replaceFragment(new WelcomeFragment(), true);
                    break;
                default:
                    Log.d(TAG, "Unhandled event: " + message);
                    break;
            }
        });
    }
}
