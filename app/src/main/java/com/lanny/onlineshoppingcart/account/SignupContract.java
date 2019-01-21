package com.lanny.onlineshoppingcart.account;

public interface SignupContract {
    interface SignupView{
        void showToast(String msg);

    }

    interface SignUp{
        void SignUpRequest(String fname, String lname, String address, String password, String email, String mobile);
    }
}
