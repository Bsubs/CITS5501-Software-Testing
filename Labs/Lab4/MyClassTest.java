package Labs.Lab4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyClassTest {
   private MyClass mc1;
   private MyClass mc2;
   private MyClass mc3;

   @BeforeEach
   public void setUp() {
     mc1 = new MyClass(3);
     mc2 = new MyClass(5);
     mc3 = new MyClass(3);
   }

   @Test
   /* Test the case when, for two objects, the second is null */
   public void equalsWhenNullRef() { 
        MyClass notInitialized = null;
        boolean x = mc1.equals(notInitialized);
        assertFalse(x, "objects should not be equal");
    }

   @Test
   /* Test the case when, for two objects, they are not equal */
   public void equalsWhenNotEq() {
        boolean x = mc1.equals(mc2);
        assertFalse(x, "mc1 does not equal mc2");
    }

   @Test
   /* Test the case when, for two objects, they are equall */
   public void equalsWhenEq() {
        boolean x = mc1.equals(mc3);
        assertTrue(x, "mc1 and mc3 should be equal");
   }

}
