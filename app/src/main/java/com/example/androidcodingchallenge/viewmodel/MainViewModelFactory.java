package com.example.androidcodingchallenge.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.androidcodingchallenge.repository.UserRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository repository;

    public MainViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

