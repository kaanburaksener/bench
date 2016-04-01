package com.kaanburaksener.bench.handler;

import android.app.Activity;
import android.content.Context;

import com.kaanburaksener.bench.handler.AuthenticationHandler;
import com.kaanburaksener.bench.core.User;

/**
 * Created by kaanburaksener on 26/03/16.
 */


/**
 * This class is created to handle the sign up and sign up requests
 */

public class AccountHandler {

    private User user;
    private Context context;
    private Activity activity;

    public AccountHandler(Context context, Activity activity){
        this.context = context;
        this.activity = activity;
    }

    /**
     * Receives data from register form
     */

    public void receiveData(String name, String email, String password){
        user = new User(name, email, password);
    }

    /**
     * Receives data from login form
     */

    public void receiveData(String email,String password){

    }

    /**
     * Request Register
     */

    public void performSignup(){
        AuthenticationHandler.register(this.user, context);
    }

    /**
     * Request Login
     */

    public void performSignin(){
        AuthenticationHandler.login(this.user, context, activity);
    }

}
