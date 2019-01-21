package com.lanny.onlineshoppingcart.account;

public interface ForgotPasswordContract {

    interface ForgotView{
        void showToast(String msg);
    }

    interface ForgotPresenter{
        void forgotPassword(String email);
    }
}
