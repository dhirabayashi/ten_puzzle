package com.github.dhirabayashi.tenpuzzle;

import com.github.dhirabayashi.tenpuzzle.data.Rational;
import com.github.dhirabayashi.tenpuzzle.rpn.RPNExpression;
import com.github.dhirabayashi.tenpuzzle.rpn.RPNUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TenPuzzle {

    private static final double ANSWER = 10.;

    public static void main(String[] args) {
        TenPuzzle tenPuzzle = new TenPuzzle();
        tenPuzzle.run();
    }

    private void run() {
        IntStream.rangeClosed(0, 9999)
                .parallel()
                .mapToObj(i -> String.format("%04d", i))
                .map(num -> new Result(num, solve(num)))
                .forEachOrdered(System.out::println);
    }

    private List<String> solve(String num) {
        return RPNUtils.createRPNExpressionPattern(num)
                .stream()
                .map(RPNExpression::new)
                .filter(rpn -> rpn.eval().orElse(new Rational(1, 1)).doubleValue() == ANSWER)
                .map(RPNExpression::toInfix)
                .distinct()
                .collect(Collectors.toList());
    }

    private static final class Result {
        private final String num;
        private final int size;
        private final String answers;

        Result(String num, List<String> list) {
            this.num = num;
            this.size = list.size();
            this.answers = size == 0 ? "" : "\t" + String.join("\t", list);
        }

        @Override
        public String toString() {
            return String.format("%s\t%d%s", num, size, answers);
        }
    }
}
