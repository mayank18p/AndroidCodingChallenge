package com.example.androidcodingchallenge.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidcodingchallenge.R;
import com.example.androidcodingchallenge.databinding.CompleteProfileFragmentBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;
import com.example.androidcodingchallenge.viewmodel.SharedViewModel;

public class CompleteProfileFragment extends Fragment {

    public static final String TAG = "CompleteProfileFragment";
    private CompleteProfileFragmentBinding binding;
    private SharedViewModel viewModel;

    public CompleteProfileFragment() {
        super(R.layout.complete_profile_fragment);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = CompleteProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");

        setData();
        setClickListeners();
        setupObservers();
    }

    private void setData() {
        UserRepository repository = new UserRepository();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);

        binding.nextButton.setOnClickListener(v -> {
            viewModel.sendEvent("Welcome Fragment");
        });
    }

    private void setClickListeners() {

    }

    private void setupObservers() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
        binding = null; // Avoid memory leaks
    }
}
