package com.example.androidcodingchallenge.ui;

import static android.view.View.VISIBLE;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidcodingchallenge.R;
import com.example.androidcodingchallenge.databinding.LoginFragmentBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.utils.ToastUtils;
import com.example.androidcodingchallenge.utils.Utils;
import com.example.androidcodingchallenge.viewmodel.SharedViewModel;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;

public class LoginFragment extends Fragment {
    public static final String TAG = "LoginFragment";
    private LoginFragmentBinding binding;
    private SharedViewModel viewModel;
    private boolean isEmailVerified = false;

    public LoginFragment() {
        super(R.layout.login_fragment);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = LoginFragmentBinding.inflate(inflater, container, false);
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

        if (Utils.isInvalidEmail(String.valueOf(binding.emailInput.getText()))) {
            binding.verifyText.setVisibility(View.GONE);
        } else {
            binding.verifyText.setVisibility(VISIBLE);
        }

        binding.emailInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable) || Utils.isInvalidEmail(editable.toString())) {
                    binding.verifyText.setVisibility(View.GONE);
                } else {
                    binding.verifyText.setVisibility(VISIBLE);
                }
            }
        });
    }

    private void setClickListeners() {
        binding.verifyText.setOnClickListener(v -> {
                    viewModel.verifyEmailApi(String.valueOf(binding.emailInput.getText()));
                });

        binding.nextButton.setOnClickListener(v -> {
            Log.d(TAG, "nextButton Click");

            if (TextUtils.isEmpty(String.valueOf(binding.firstNameInput.getText())) || TextUtils.isEmpty(String.valueOf(binding.lastNameInput.getText())) || TextUtils.isEmpty(String.valueOf(binding.emailInput.getText()))) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.all_fields_are_required));
            } else if (Utils.isInvalidEmail(String.valueOf(binding.emailInput.getText()))) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.invalid_email_address));
            } else if(!isEmailVerified) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.email_not_verified_yet));
            } else if(TextUtils.isEmpty(String.valueOf(binding.companyNameInput.getText()))) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.company_name_should_not_empty));
            } else {
                // Set the user data in the ViewModel
                viewModel.setFirstName(String.valueOf(binding.firstNameInput.getText()));
                viewModel.setLastName(String.valueOf(binding.lastNameInput.getText()));
                viewModel.setEmail(String.valueOf(binding.emailInput.getText()));
                viewModel.setCompany(String.valueOf(binding.companyNameInput.getText()));
            }
//            viewModel.sendOTPApi(email);
        });
    }

    private void setupObservers() {
        viewModel.getEmailVerifyStatus().observe(getViewLifecycleOwner(), apiStatus -> {
            isEmailVerified = apiStatus;
            if (isEmailVerified && !TextUtils.isEmpty(String.valueOf(binding.firstNameInput.getText())) && !TextUtils.isEmpty(String.valueOf(binding.lastNameInput.getText())) && !TextUtils.isEmpty(String.valueOf(binding.emailInput.getText())) && !TextUtils.isEmpty(String.valueOf(binding.companyNameInput.getText()))) {
                binding.nextButton.setEnabled(true);
                binding.nextButton.setClickable(true);
                binding.nextButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.enable_btn_bg));
                binding.verifyText.setTextColor(getResources().getColor(R.color.verified_color));

                binding.companyNameText.setVisibility(VISIBLE);
                binding.companyNameInput.setVisibility(VISIBLE);
            } else {
                binding.nextButton.setEnabled(false);
                binding.nextButton.setClickable(false);
                binding.nextButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.disable_btn_bg));
                binding.verifyText.setTextColor(getResources().getColor(R.color.unverified_color));
            }
        });

        viewModel.getSendOtpStatus().observe(getViewLifecycleOwner(), apiStatus -> {
            if (apiStatus) {
                // Navigate to the OTP Fragment
                viewModel.sendEvent("Profile Fragment");
            }
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!TextUtils.isEmpty(message)) {
                ToastUtils.showShortToast(requireActivity(), message);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
