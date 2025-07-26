package com.example.androidcodingchallenge.ui;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidcodingchallenge.R;
import com.example.androidcodingchallenge.databinding.CompleteProfileFragmentBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.utils.ToastUtils;
import com.example.androidcodingchallenge.utils.Utils;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;
import com.example.androidcodingchallenge.viewmodel.SharedViewModel;
import java.util.Calendar;

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

        String htmlText = getString(R.string.terms_of_service_and_privacy_policy);
        binding.termsAndPolicyText.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY));
    }

    private void setClickListeners() {

        binding.birthdayInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String formattedDate = String.format("%02d/%02d/%04d", month + 1, dayOfMonth, year);
                                binding.birthdayInput.setText(formattedDate);
                            }
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });

        binding.nextButton.setOnClickListener(v -> {

            if (TextUtils.isEmpty(String.valueOf(binding.passwordInput.getText())) || TextUtils.isEmpty(String.valueOf(binding.confirmPasswordInput.getText()))
                    || TextUtils.isEmpty(String.valueOf(binding.birthdayInput.getText())) || TextUtils.isEmpty(String.valueOf(binding.contactNumberInput.getText()))) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.all_fields_are_required));

            } else if(!String.valueOf(binding.passwordInput.getText()).equals(String.valueOf(binding.confirmPasswordInput.getText()))) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.passwords_dont_match_please_reenter));

            } else if(binding.passwordInput.length() < 8 || binding.confirmPasswordInput.length() < 8) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.passwords_must_be_eight_char_log));

            } else if(!Utils.isValidDateFormat(String.valueOf(binding.birthdayInput.getText()))) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.invalid_date_format));

            } else if(binding.contactNumberInput.length() < 10) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.contact_must_be_ten_digits_long));

            } else if(!binding.checkBox.isChecked()) {
                ToastUtils.showShortToast(requireActivity(), getString(R.string.please_accept_terms_and_privacy_policy));

            } else {
                enableNextButton();

                // Call User Registration OTP API here
                viewModel.userRegistrationApi(viewModel.getFirstName().getValue(), viewModel.getLastName().getValue(), viewModel.getEmail().getValue(),
                        String.valueOf(binding.passwordInput.getText()), Utils.convertDateFormat(String.valueOf(binding.birthdayInput.getText())),
                        viewModel.getToken().getValue(), Long.parseLong(String.valueOf(binding.contactNumberInput.getText())));
            }
        });


        binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!TextUtils.isEmpty(String.valueOf(binding.passwordInput.getText())) || !TextUtils.isEmpty(String.valueOf(binding.confirmPasswordInput.getText()))
                        || !TextUtils.isEmpty(String.valueOf(binding.birthdayInput.getText())) || !TextUtils.isEmpty(String.valueOf(binding.contactNumberInput.getText()))) {
                    enableNextButton();
                } else {
                    disableNextButton();
                }
                } else {
                disableNextButton();
            }
        });
    }
    private void setupObservers() {
        viewModel.getUserRegisteredStatus().observe(getViewLifecycleOwner(), apiStatus -> {
            if (apiStatus) {
                // Navigate to the Welcome Fragment
                viewModel.sendEvent("Welcome Fragment");
            }
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!TextUtils.isEmpty(message)) {
                ToastUtils.showShortToast(requireActivity(), message);
            }
        });
    }

    private void disableNextButton() {
        binding.nextButton.setEnabled(false);
        binding.nextButton.setClickable(false);
        binding.nextButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.disable_btn_bg));
    }

    private void enableNextButton() {
        binding.nextButton.setEnabled(true);
        binding.nextButton.setClickable(true);
        binding.nextButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.enable_btn_bg));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
        binding = null; // Avoid memory leaks
    }
}
