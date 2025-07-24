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

    private String email;

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

        UserRepository repository = new UserRepository();
        MainViewModelFactory factory = new MainViewModelFactory(repository);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);

        email = String.valueOf(binding.emailInput.getText());

        if (Utils.isInvalidEmail(email)) {
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

        binding.verifyText.setOnClickListener(v -> {
//                     viewModel.verifyEmailApi(email);
                    viewModel.sendEvent("Profile Fragment");
                    binding.companyNameText.setVisibility(VISIBLE);
                    binding.companyNameInput.setVisibility(VISIBLE);
                }
        );


        binding.nextButton.setOnClickListener(v -> {
            String fname = String.valueOf(binding.firstNameInput.getText());
            String lname = String.valueOf(binding.lastNameInput.getText());
            String email = String.valueOf(binding.emailInput.getText());
            String company = String.valueOf(binding.companyNameInput.getText());

            if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(lname) || TextUtils.isEmpty(email) || TextUtils.isEmpty(company)) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.all_fields_are_required));
            } else if (Utils.isInvalidEmail(email)) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.invalid_email_address));
            } else {
                // Set the user data in the ViewModel
                viewModel.setFirstName(fname);
                viewModel.setLastName(lname);
                viewModel.setEmail(email);
                viewModel.setCompany(company);

                // viewModel.sendOTPApi(email);

                viewModel.sendEvent("Otp Fragment");
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
