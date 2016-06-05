package com.github.dhirabayashi.tenpuzzle.rpn;

import com.github.dhirabayashi.tenpuzzle.data.Rational;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RPNExpressionTest {
    @Test
    public void test_add() {
        Optional<Rational> actual = new RPNExpression("42+").eval();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().doubleValue(), is(6.));
    }

    @Test
    public void test_subtract() {
        Optional<Rational> actual = new RPNExpression("42-").eval();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().doubleValue(), is(2.));

        actual = new RPNExpression("16-").eval();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().doubleValue(), is(-5.));
    }

    @Test
    public void test_multiply() {
        Optional<Rational> actual = new RPNExpression("42*").eval();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().doubleValue(), is(8.));
    }

    @Test
    public void test_divide() {
        Optional<Rational> actual = new RPNExpression("82/").eval();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().doubleValue(), is(4.));

        actual = new RPNExpression("52/").eval();
        assertThat(actual.isPresent(), is(true));
        assertThat(actual.get().doubleValue(), is(2.5));

        actual = new RPNExpression("50/").eval();
        assertThat(actual.isPresent(), is(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_illegalOperator() {
        new RPNExpression("82?");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_tooManyOperand() {
        new RPNExpression("12+3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_tooFewOperand() {
        new RPNExpression("1+");
    }

    // NNNNooo
    @Test
    public void test_pattern1() {
        Optional<Rational> actual = new RPNExpression("1234+-*").eval();
        assertThat(actual.get().doubleValue(), is(-5.));
    }

    // NNNoNoo
    @Test
    public void test_pattern2() {
        Optional<Rational> actual = new RPNExpression("873/3*-").eval();
        assertThat(actual.get().doubleValue(), is(1.));
    }

    // NNoNNoo
    @Test
    public void test_pattern3() {
        Optional<Rational> actual = new RPNExpression("03+56/*").eval();
        assertThat(actual.get().doubleValue(), is(2.5));
    }

    // NNoNoNo
    @Test
    public void test_pattern4() {
        Optional<Rational> actual = new RPNExpression("56*8+3+").eval();
        assertThat(actual.get().doubleValue(), is(41.));
    }

    // NNNooNo
    @Test
    public void test_pattern5() {
        Optional<Rational> actual = new RPNExpression("467+-2-").eval();
        assertThat(actual.get().doubleValue(), is(-11.));
    }

    @Test
    public void test_toInfix() {
        assertThat(new RPNExpression("12+").toInfix(), is("1+2"));

        assertThat(new RPNExpression("1234+-*").toInfix(), is("1*(2-(3+4))"));
        assertThat(new RPNExpression("873/3*-").toInfix(), is("8-((7/3)*3)"));
        assertThat(new RPNExpression("03+56/*").toInfix(), is("(0+3)*(5/6)"));
        assertThat(new RPNExpression("56*8+3+").toInfix(), is("((5*6)+8)+3"));
        assertThat(new RPNExpression("467+-2-").toInfix(), is("(4-(6+7))-2"));
    }
}
