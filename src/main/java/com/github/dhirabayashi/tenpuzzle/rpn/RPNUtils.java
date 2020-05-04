package com.github.dhirabayashi.tenpuzzle.rpn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 逆ポーランド記法のユーティリティ
 */
public final class RPNUtils {
    private RPNUtils(){}

    /**
     * オペランドの数
     */
    private static final int NUM_LENGTH = 4;

    /**
     * 演算子リスト
     */
    private static final List<Character> OPERATORS = Arrays.asList('+', '-', '*', '/');

    /**
     * 式の桁数
     */
    private static final int EXPRESSION_LENGTH = NUM_LENGTH + OPERATORS.size() - 1;


    /**
     * num を元にありうる逆ポーランド記法の式の全パターンを生成する。戻り値を返さずresult の状態を変更する
     * @param num 対象の数字
     * @param result 式を追加するList
     * @param expressions 逆ポーランド記法の式
     * @param numDeterminedFlags 数値が決まったかどうかのフラグ
     * @param numCount 式が含む数値の数
     * @param operatorCount 式が含む演算子の数
     */
    private static void createRPNExpressionPattern(String num, List<String> result, char[] expressions, boolean[] numDeterminedFlags, int numCount, int operatorCount) {
        // 数値と演算子が全て決まったかどうか
        if(numCount + operatorCount == EXPRESSION_LENGTH) {
            result.add(new String(expressions));
            return;
        }

        // 演算子を入れることができるか
        // 演算子の数 = 数値の数 - 1のため、差が2以上あれば演算子を入れる余地がある
        if(2 <= numCount - operatorCount) {
            OPERATORS.forEach(c-> {
                expressions[numCount + operatorCount] = c;
                createRPNExpressionPattern(num, result, expressions, numDeterminedFlags, numCount, operatorCount + 1);
            });
        }

        // 数値を入れることができるかどうか
        if(numCount < NUM_LENGTH) {
            for(int i = 0; i < NUM_LENGTH; i++) {
                if(!numDeterminedFlags[i]) {
                    numDeterminedFlags[i] = true;
                    expressions[numCount + operatorCount] = num.toCharArray()[i];
                    createRPNExpressionPattern(num, result, expressions, numDeterminedFlags, numCount + 1, operatorCount);
                    numDeterminedFlags[i] = false;
                }
            }
        }
    }

    /**
     * 4桁の数字を元にありうる逆ポーランド記法の式の全パターンを生成する。
     * @param num 対象の4桁の数字。
     * @throws IllegalArgumentException num が4桁の数字ではない場合
     * @return 逆ポーランド記法の式のList
     */
    public static List<String> createRPNExpressionPattern(String num) {
        if(num == null || !num.matches("^\\d{4}$")) {
            throw  new IllegalArgumentException("num is illegal format.");
        }

        List<String> result = new ArrayList<>();

        // boolean の初期値はfalse
        createRPNExpressionPattern(num, result, new char[EXPRESSION_LENGTH], new boolean[NUM_LENGTH], 0, 0);
        // list は可変なので状態が変更されている
        return result;
    }
}
