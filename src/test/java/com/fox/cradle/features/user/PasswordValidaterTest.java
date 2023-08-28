package com.fox.cradle.features.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordValidaterTest
{
    @Test
    public void isPasswordValidTest()
    {
        String password = "this is a 5 Good password";
        boolean actual = PasswordValidater.isValid(password);
        Assertions.assertTrue(actual, "password meets all criteria");
    }

    @Test
    public void passwordHasLowAndHighCase()
    {
        String password = "Tffffgggggggg";
        boolean actual = PasswordValidater.hasAtleastOneBigAndOneSmall(password);
        Assertions.assertTrue(actual, "password has lower and big character");
    }
    @Test
    public void passwordHasNotLowAndHighCase()
    {
        String password = "wffffggggg5ggg";
        boolean actual = PasswordValidater.hasAtleastOneBigAndOneSmall(password);
        Assertions.assertFalse(actual, "password has only smaller case letters");

        String password_2 = "RRRRRRRRRTT5TTTTTTTT";
        boolean actual_2 = PasswordValidater.hasAtleastOneBigAndOneSmall(password);
        Assertions.assertFalse(actual_2, "password has only uppercase letters");
    }

    @Test
    public void returnTrueIfPasswordLengh8orhigher()
    {
        String password = "myextralongpassword";

        boolean actual = PasswordValidater.isPasswordLenghtOK(password);

        Assertions.assertTrue(actual , "password longer then 8, should be true");
    }

    @Test
    public void returnFalseIfPasswordSmaller8()
    {
        String password = "toshort";

        boolean actual = PasswordValidater.isPasswordLenghtOK(password);

        Assertions.assertFalse(actual , "password smaller then 8, should be false");
    }

    @Test
    public void returnTrueIfNumberInPassword()
    {
        String password = "test1blub3";
        boolean actual = PasswordValidater.isNumberIncluded(password);
        Assertions.assertTrue(actual, "password has number");
    }

    @Test
    public void returnFalsIfNoNumberInPassword()
    {
        String password = "testtesttest";
        boolean actual = PasswordValidater.isNumberIncluded(password);
        Assertions.assertFalse(actual, "no number in password gives error");
    }

    @Test
    public void testContainsNoForbiddenWords()
    {
        String password = "Cool this is a 4 password";
        boolean actual = PasswordValidater.isPasswordComplexEnough(password);
        Assertions.assertTrue(actual, "Password is complex enough");
    }

    @Test
    public void testForbiddenWords()
    {
        String password = "Password123";
        boolean actual = PasswordValidater.isPasswordComplexEnough(password);
        Assertions.assertFalse(actual, "tests a forbidden password");
    }

    @Test
    public void passwordHasSpecialCharacter()
    {
        String password = "i need a lot of $$$$";
        boolean actual = PasswordValidater.hasSpecialCharacters(password);
        Assertions.assertTrue(actual, "Password has special character returns true");
    }

    @Test
    public void passwordHasNoSpecialCharacter()
    {
        String password = "i need a lot of somthing";
        boolean actual = PasswordValidater.hasSpecialCharacters(password);
        Assertions.assertTrue(actual, "Password has no special character returns false");
    }

}
