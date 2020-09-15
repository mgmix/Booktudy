import org.junit.Assert;
import org.junit.Test;

public class TestCase {

    @Test
    public void testMultiplication() {
        Money five = Money.dollar(5);
        Assert.assertEquals(Money.dollar(10), five.times(2));
        Assert.assertEquals(Money.dollar(20), five.times(4));
    }

    @Test
    public void testEquality() {
        Assert.assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        Assert.assertFalse(Money.dollar(5).equals(Money.dollar(10)));
        Assert.assertFalse(Money.dollar(10).equals(Money.franc(10)));
    }

    @Test
    public void testCurrency() {
        Assert.assertEquals("USD", Money.dollar(1).currency());
        Assert.assertEquals("CHF", Money.franc(1).currency());
        Assert.assertFalse(Money.franc(1).currency().equals("USD"));
        Assert.assertFalse(Money.dollar(1).currency().equals("CHF"));
    }

    @Test
    public void testSimpleAddition() {
        Expression sum = Money.dollar(5).plus(Money.dollar(5));
        Bank bank = new Bank();
        Money reduced = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(10), reduced);
    }

    @Test
    public void testPlusReturnsSum() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        Assert.assertEquals(five, sum.augend);
        Assert.assertEquals(five, sum.addend);
    }

    @Test
    public void testBankReduceSum() {
        Expression sum = new Sum(Money.dollar(5), Money.dollar(10));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(15), result);
    }

    @Test
    public void testReduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        Assert.assertEquals(Money.dollar(1), result);
    }

    @Test
    public void testReduceMoneyDifferentCurrency() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        Assert.assertEquals(Money.dollar(1), result);
    }

    @Test
    public void testIdentityRate() {
        Assert.assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    public void testMixedAddition() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
        Assert.assertEquals(Money.dollar(10), result);
    }

    @Test
    public void testSumPlusMoney() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        // Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        // 이렇게 표현하는게 Sum 을 테스트하는데 직접적인 의도를 드러낼 수 있음
        Expression sum = fiveBucks.plus(tenFrancs).plus(fiveBucks);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(sum, "USD");
        Assert.assertEquals(Money.dollar(15), result);
    }

    @Test
    public void testSumTimes() {
        Expression fiveBucks = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Expression times = new Sum(fiveBucks, tenFrancs).times(2);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(times, "USD");
        Assert.assertEquals(Money.dollar(20), result);
    }

}
