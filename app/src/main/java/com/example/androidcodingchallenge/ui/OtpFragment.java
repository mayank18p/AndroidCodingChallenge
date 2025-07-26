package com.example.androidcodingchallenge.ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.androidcodingchallenge.utils.Utils.hideKeyboardFromFragment;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.androidcodingchallenge.databinding.OtpFragmentBinding;
import com.example.androidcodingchallenge.repository.UserRepository;
import com.example.androidcodingchallenge.utils.TimerUtils;
import com.example.androidcodingchallenge.viewmodel.MainViewModelFactory;
import com.example.androidcodingchallenge.viewmodel.SharedViewModel;
import in.aabhasjindal.otptextview.OTPListener;

public class OtpFragment extends Fragment {
    public static final String TAG = "OtpFragment";
    private OtpFragmentBinding binding;
    private SharedViewModel viewModel;

    public OtpFragment() {
        super(R.layout.otp_fragment);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        binding = OtpFragmentBinding.inflate(inflater, container, false);
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

        binding.otpView.requestFocusOTP();
        binding.otpView.requestFocus();
        binding.otpView.setFocusable(true);
        binding.otpView.setEnabled(true);
        binding.otpView.setClickable(true);
        binding.otpView.setFocusableInTouchMode(true);

        TimerUtils.startOtpTimer(binding.resendText, getContext());
    }

    private void setClickListeners() {

        binding.otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {
                binding.incorrectOTPText.setVisibility(GONE);
                binding.submitButton.setClickable(false);
                binding.submitButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.disable_btn_bg));
            }
            @Override
            public void onOTPComplete(@NonNull String otp) {
                binding.incorrectOTPText.setVisibility(GONE);

                if(otp.length()==6) {
                    binding.submitButton.setEnabled(true);
                    binding.submitButton.setClickable(true);
                    binding.submitButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.enable_btn_bg));

                    hideKeyboardFromFragment(requireActivity(),  binding.otpView);
                    verifyOtp();
                } else {
                    binding.submitButton.setClickable(false);
                    binding.submitButton.setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.disable_btn_bg));
                }
            }
        });

        binding.resendText.setOnClickListener(v -> {
            if (TimerUtils.countDownTimer != null) {
                TimerUtils.countDownTimer.cancel();
            }
            // Call send OTP API here
            viewModel.sendOTPApi(viewModel.getEmail().getValue());
            TimerUtils.startOtpTimer(binding.resendText, getContext());
        });
    }

    private void verifyOtp() {
        binding.submitButton.setOnClickListener(v -> {
            // Call Verify OTP API here
            viewModel.verifyOTPApi(viewModel.getToken().getValue(), Integer.valueOf(binding.otpView.getOTP().trim()));
        });
    }

    private void setupObservers() {
        viewModel.getVerifyOtpStatus().observe(getViewLifecycleOwner(), apiStatus -> {
            if (apiStatus) {
                // Navigate to the Profile Fragment
                binding.incorrectOTPText.setVisibility(GONE);
                viewModel.sendEvent("Profile Fragment");
            }
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), message -> {
            if (!TextUtils.isEmpty(message)) {
                binding.incorrectOTPText.setText(message);
                binding.incorrectOTPText.setVisibility(VISIBLE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
        binding = null; // Avoid memory leaks
    }

}
