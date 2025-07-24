package com.example.androidcodingchallenge.network;

public abstract class Resource<T> {

    public final T data;
    public final String message;

    private Resource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    // Success subclass
    public static final class Success<T> extends Resource<T> {
        public Success(T data) {
            super(data, null);
        }
    }

    // Error subclass
    public static final class Error<T> extends Resource<T> {
        public Error(String message) {
            super(null, message);
        }
    }

    // Loading subclass
    public static final class Loading<T> extends Resource<T> {
        public Loading() {
            super(null, null);
        }
    }
}
