package com.example.androidcodingchallenge.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.androidcodingchallenge.repository.UserRepository;

public class MainViewModel extends ViewModel {
    public static final String TAG = "MainViewModel";
    private final UserRepository repository;
    public MainViewModel(UserRepository repository) {
        this.repository = repository;
    }
}
