package com.google.firebase.udacity.friendlychat.test;

/**
 * Created by breandan on 31/03/17.
 */

import com.google.firebase.udacity.friendlychat.Sign_Up;

import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class Sign_UpTest {

    // email test must not have '@' or '.' at start or end
    // "             " have an '@' and 'tcd.ie'
    // the @ must come before the '.'

    @Test
    public void testEmailInvalid1(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("abc123") == false);
    }

    @Test
    public void testEmailInvalid2(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("@tcd.ielol") == false);
    }

    @Test
    public void testEmailInvalid3(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("hello.tcd.ie@") == false);
    }

    @Test
    public void testEmailInvalid4(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("hello.tcd.ie.") == false);
    }

    @Test
    public void testEmailInvalid5(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("@hello.tcd.ie") == false);
    }

    @Test
    public void testEmailInvalid6(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("John@hotmail.com") == false);
    }


    @Test
    public void testEmailInvalid(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was invalid", session.validEmail("Ahello.tcd.ie") == false);
    }


    @Test
    public void testEmailValid1(){
        Sign_Up session = new Sign_Up();
        assertTrue("Email entered was valid", session.validEmail("abc123@tcd.ie") == true);
    }

    // passwords must be at least 8 digits long
    // have at least 1 upper and 1 lower case letter a number and a special character
    // He)9He)9He(9

    @Test
            (expected = Exception.class)
    public void testPasswordLength1(){
        Sign_Up session = new Sign_Up();
        assertTrue("Password min length is 8", session.checkPassword("He)9He)") == true);
    }

    @Test
    public void testPasswordLength2(){
        try{
            Sign_Up session = new Sign_Up();
            assertTrue("Password min length is 8", session.checkPassword("He)9He)9He(9") == true);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
