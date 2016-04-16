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
    private int userID;
    private String name;
    private String email;
    private String password;
    private String location;
    private String birthday;
    private Context context;
    private Activity activity;
    private Context windowContext;

    public AccountHandler(Activity activity, Context context, Context windowContext){
        this.context = context;
        this.activity = activity;
        this.windowContext = windowContext;
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

    public void receiveData(int userID, String location,String birthday){
        this.userID = userID;
        this.location = location;
        this.birthday = birthday;
    }

    public void performSignup(){
        AccountAuthenticator.signupUser(name, email, password, activity, context, windowContext);
    }

    public void performSignin(){
        AccountAuthenticator.signinUser(email, password, activity, context, windowContext);
    }

    public void performUpdate(){
        AccountAuthenticator.updateUser(userID, location, birthday, activity, context, windowContext);
    }
}
