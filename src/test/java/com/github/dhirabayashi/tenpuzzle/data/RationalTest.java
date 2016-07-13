package com.github.dhirabayashi.tenpuzzle.data;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RationalTest {
    @Test
    public void test_constructor() {
        Rational sut = new Rational(1, 2);
        assertThat(sut.toString(), is("1/2"));
        assertThat(sut.getNumerator(), is(1));
        assertThat(sut.getDenominator(), is(2));
        assertThat(Double.compare(sut.doubleValue(), 1. / 2.), is(0));

        sut = new Rational(4, -3);
        assertThat(sut.toString(), is("4/-3"));
        assertThat(sut.getNumerator(), is(4));
        assertThat(sut.getDenominator(), is(-3));
        assertThat(Double.compare(sut.doubleValue(), 4. / -3.), is(0));

        sut = new Rational(-2, 10);
        assertThat(sut.toString(), is("-1/5"));
        assertThat(sut.getNumerator(), is(-1));
        assertThat(sut.getDenominator(), is(5));
        assertThat(Double.compare(sut.doubleValue(), -2. / 10.), is(0));

        sut = new Rational(-15, -35);
        assertThat(sut.toString(), is("-3/-7"));
        assertThat(sut.getNumerator(), is(-3));
        assertThat(sut.getDenominator(), is(-7));
        assertThat(Double.compare(sut.doubleValue(), -3. / -7.), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_zeroDenominator() {
        new Rational(1, 0);
    }

    @Test
    public void test_add() {
        Rational actual = new Rational(2, 7).add(new Rational(3, 7));
        assertThat(actual.toString(), is("5/7"));

        actual = new Rational(1, 2).add(new Rational(1, 2));
        assertThat(actual.toString(), is("1/1"));

        actual = new Rational(2, 3).add(new Rational(-1, 4));
        assertThat(actual.toString(), is("5/12"));
    }

    @Test
    public void test_subtract() {
        Rational actual = new Rational(4, 5).subtract(new Rational(2, 7));
        assertThat(actual.toString(), is("18/35"));

        actual = new Rational(9, 4).subtract(new Rational(6, 8));
        assertThat(actual.toString(), is("3/2"));

        actual = new Rational(1, 3).subtract(new Rational(1, 2));
        assertThat(actual.toString(), is("-1/6"));
    }

    @Test
    public void test_multiply() {
        Rational actual = new Rational(2, 5).multiply(new Rational(2, 3));
        assertThat(actual.toString(), is("4/15"));

        actual = new Rational(-1, 4).multiply(new Rational(2, 8));
        assertThat(actual.toString(), is("-1/16"));

        actual = new Rational(-3, 7).multiply(new Rational(-2, 5));
        assertThat(actual.toString(), is("6/35"));
    }

    @Test
    public void test_divide() {
        Rational actual = new Rational(2, 5).divide(new Rational(2, 3));
        assertThat(actual.toString(), is("3/5"));

        actual = new Rational(-1, 4).divide(new Rational(2, 8));
        assertThat(actual.toString(), is("-1/1"));

        actual = new Rational(-3, 7).divide(new Rational(-2, 5));
        assertThat(actual.toString(), is("-15/-14"));
    }

    @Test(expected = ArithmeticException.class)
    public void test_divide_byZero() {
        new Rational(1, 1).divide(new Rational(0, 1));
    }
}
