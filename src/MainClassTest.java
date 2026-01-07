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
}
