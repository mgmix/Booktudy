import org.junit.Assert;
import org.junit.Test;

public class TestCase {

    @Test
    public void testDollarMultiplication() {
        Dollar five = new Dollar(5);
        Assert.assertEquals(new Dollar(10), five.times(2));
        Assert.assertEquals(new Dollar(15), five.times(3));
    }

    @Test
    public void testFrancMultiplication() {
        Franc five = new Franc(5);
        Assert.assertEquals(new Franc(10), five.times(2));
        Assert.assertEquals(new Franc(15), five.times(3));
    }

    @Test
    public void testEquality() {
        Assert.assertTrue(new Dollar(5).equals(new Dollar(5)));
        Assert.assertFalse(new Dollar(5).equals(new Dollar(10)));
        Assert.assertTrue(new Franc(5).equals(new Franc(5)));
        Assert.assertFalse(new Franc(5).equals(new Franc(10)));
    }



}
