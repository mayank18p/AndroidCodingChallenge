package com.example.androidcodingchallenge.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.example.androidcodingchallenge.R;
import com.example.androidcodingchallenge.databinding.WelcomeFragmentBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.utils.ToastUtils;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;
import com.example.androidcodingchallenge.viewmodel.SharedViewModel;

public class WelcomeFragment extends Fragment {
    public static final String TAG = "WelcomeFragment";
    private WelcomeFragmentBinding binding;
    private SharedViewModel viewModel;

    public WelcomeFragment() {
        super(R.layout.welcome_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = WelcomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");

        setData();
        setClickListeners();
        showWelcomePage();
    }

    private void setData() {
        UserRepository repository = new UserRepository();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);

        Glide.with(requireActivity()).load(R.drawable.running_ic).into(binding.ivRunner);
    }

    private void setClickListeners() {
        binding.btnGetStarted.setOnClickListener(v -> {
            ToastUtils.showShortToast(requireActivity(), getString(R.string.thank_you));
        });
    }

    private void showWelcomePage() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            binding.ivRunner.setVisibility(GONE);
            binding.tvLoadingMessage.setVisibility(GONE);
            binding.ivWelcome.setVisibility(VISIBLE);
            binding.tvWelcomeTitle.setVisibility(VISIBLE);
            binding.tvDescription.setVisibility(VISIBLE);
            binding.btnGetStarted.setVisibility(VISIBLE);
        }, 3000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
        binding = null; // Avoid memory leaks
    }
}
