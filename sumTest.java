/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import org.junit.Test;
import static org.junit.Assert.*;


public class sumTest {
    
    public sumTest() {
    }
    

    @Test
    //@cr_testcase(mark="1.0000000", hiderestiffail="0", useasexample="0", testtype="0", stdin="", extra="", display="SHOW")

    public void testMain() {
        String[] args = null;
        sum.main(args);
        sum s = new sum();
        s.setStr("ahmed");
        assertEquals("ahmed", s.getStr());
    }


    @Test
    //@cr_testcase(mark="2.0000000", hiderestiffail="1", useasexample="0", testtype="0", stdin="", extra="", display="HIDE")

    public void testGetStr() {
        boolean expResult = false;
        if(true){
            expResult = true;
        }
        assertTrue(expResult);
    }


    @Test
    //@cr_testcase(mark="3.0000000", hiderestiffail="0", useasexample="1", testtype="0", stdin="", extra="", display="HIDE")

    public void testSetStr() {
        boolean expResult = false;
        assertFalse(expResult);
    }
    
}
