import org.junit.Assert;
import org.junit.Test;

public class MainClassTest extends MainClass {
    @Test
    public void testGetLocalNumber(){
        if (this.getLocalNumber() == 14) {
            System.out.println("Local number is: " + this.getLocalNumber());
        }
        else {
            Assert.fail("Local number is not 14");
        }
    }

    @Test
    public void testGetClassNumber(){
        if (this.getClassNumber() > 45) {
            System.out.println("Class Number is greater than 45");
        }
        else {
            Assert.fail("Class number is less than 45");
        }
    }

    @Test
    public void testGetClassString(){
        Assert.assertTrue(this.getClassString().contains("hello") || this.getClassString().contains("Hello"));
        }
    }
