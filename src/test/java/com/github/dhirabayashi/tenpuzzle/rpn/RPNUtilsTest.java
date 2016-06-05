package com.github.dhirabayashi.tenpuzzle.rpn;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RPNUtilsTest {
    @Test(expected = IllegalArgumentException.class)
    public void test_createRPNExpressionPattern_null() {
        RPNUtils.createRPNExpressionPattern(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createRPNExpressionPattern_not_four() {
        RPNUtils.createRPNExpressionPattern("12345");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_createRPNExpressionPattern_not_num() {
        RPNUtils.createRPNExpressionPattern("123a");
    }

    @Test
    public void test_createRPNExpressionPattern() throws Exception {
        List<String> actual = RPNUtils.createRPNExpressionPattern("2501");
        actual.sort(Comparator.naturalOrder());

        Path path = Paths.get(this.getClass().getClassLoader().getResource("expected2501.txt").toURI());
        List<String> expected = Files.readAllLines(path);
        expected.sort(Comparator.naturalOrder());

        assertThat(actual, is(expected));
    }
}
