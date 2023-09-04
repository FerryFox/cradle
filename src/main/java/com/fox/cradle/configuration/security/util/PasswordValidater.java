package com.fox.cradle.configuration.security.util;

public class PasswordValidater
{
    private static final int PASSWORD_MIN_Lenght = 8;

    //the last ones are not needed since ther are not allowed anyway
    private static final String[] FORBIDDEN_PASSWORDS = {"Password123", "Password", "12345678"};
    public static boolean isValid(String password)
    {
        //just in case some persons copyed ther password
        password = password.trim();

        if(!isPasswordLenghtOK(password)) return false;

        if(!isNumberIncluded(password)) return false;

        if(!hasAtleastOneBigAndOneSmall(password)) return false;

        if(!isPasswordComplexEnough(password)) return false;

        if(!hasSpecialCharacters(password)) return false;

        return true;
    }

    public static boolean isPasswordLenghtOK(String password)
    {
        if(password.length() < PASSWORD_MIN_Lenght)
        {
            return false;
        }
        return true;
    }

    public static boolean isNumberIncluded(String password)
    {
        boolean hasDigit = false;
        for(char c : password.toCharArray())
        {
            if(Character.isDigit(c)) hasDigit = true;
        }
        return hasDigit;
    }

    public static boolean hasAtleastOneBigAndOneSmall(String password)
    {
        boolean hasBig = false;
        boolean hasSmall = false;

        for(char c : password.toCharArray())
        {
            if(Character.isLetter(c))
            {
                if(Character.isLowerCase(c)) hasSmall = true;
                if(Character.isUpperCase(c)) hasBig = true;
            }
        }

        return (hasBig && hasSmall);
    }

    public static boolean isPasswordComplexEnough(String password)
    {
        for(String forbidden : FORBIDDEN_PASSWORDS)
        {
            if(forbidden.equals(password)) return false;
        }
        return true;
    }

    public static boolean hasSpecialCharacters(String password)
    {
        boolean hasSpecial = false;
        for(char c : password.toCharArray())
        {
            if(Character.isWhitespace(c)) continue;
            if(!Character.isLetter(c) && !Character.isDigit(c))
            {
                hasSpecial = true;
            }
        }
        return hasSpecial;
    }
}
