package com.devpedrod.apiuserregister.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator {

    public static Boolean validateName(String name) {
        Pattern pattern = Pattern.compile("^[a-zA-ZÀ-ú]+([ ']?[a-zA-ZÀ-ú])*$");
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }
}
