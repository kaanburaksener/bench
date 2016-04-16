package com.kaanburaksener.bench.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kaanburaksener on 01/04/16.
 */
public class Helper {

    /**
     * This function is used to check the validity of the input entered as email
     */

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid = true;
        }

        return isValid;
    }
}
