package com.google.firebase.udacity.friendlychat.test;

/**
 * Created by breandan on 31/03/17.
 */


import com.google.firebase.udacity.friendlychat.MainActivity;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;
import org.junit.Test;


public class MainActivityTest {
    ArrayList<String> list = new ArrayList<String>();


    @Test
    public void testIsItemInArray(){
        MainActivity session = new MainActivity();

        //method myPid not mocked?
        assertTrue("Module is in list", session.isItemInArray("3D5B", session.modules, 1) == true);

    }
}
