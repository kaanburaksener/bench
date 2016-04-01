package com.kaanburaksener.bench.handler;

import android.app.Activity;
import android.content.Context;

import com.kaanburaksener.bench.authentication.AccountAuthenticator;

/**
 * Created by kaanburaksener on 26/03/16.
 */

/**
 * This class is created to handle the sign up and sign up requests
 */

public class AccountHandler {
    private String name;
    private String email;
    private String password;
    private Context context;
    private Activity activity;

    public AccountHandler(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    public void receiveData(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void receiveData(String email,String password){
        this.email = email;
        this.password = password;
    }

    public void performSignup(){
        AccountAuthenticator.signup_user(name, email, password, context);
    }

    public void performSignin(){
        AccountAuthenticator.signin_user(email, password, context, activity);
    }

}
