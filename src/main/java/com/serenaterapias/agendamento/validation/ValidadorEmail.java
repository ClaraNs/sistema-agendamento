package com.serenaterapias.agendamento.validation;

import java.util.regex.Pattern;

public class ValidadorEmail {
    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email){
        if(email == null || email.isEmpty())
            return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
