package com.github.dhirabayashi.tenpuzzle.rpn;

import org.eclipse.collections.api.list.primitive.CharList;
import org.eclipse.collections.impl.factory.primitive.CharLists;

import java.util.ArrayList;
import java.util.List;

/**
 * 逆ポーランド記法のユーティリティ
 */
public final class RPNUtils {
    private RPNUtils(){}

    /**
     * オペランドの数
     */
    private static final int OPERAND_LENGTH = 4;

    /**
     * 演算子リスト
     */
    private static final CharList OPERATORS = CharLists.immutable.of('+', '-', '*', '/');

    /**
     * 式の桁数
     */
    private static final int EXPRESSION_LENGTH = OPERAND_LENGTH + OPERATORS.size() - 1;


    /**
     * num を元にありうる逆ポーランド記法の式の全パターンを生成する。戻り値を返さずlist の状態を変更する
     * @param num 対象の数字
     * @param list 式を追加するList
     * @param expression 逆ポーランド記法の式
     * @param flag 数値が決まったかどうかのフラグ
     * @param numCount 式が含む数値の数
     * @param operatorCount 式が含む演算子の数
     */
    private static void createRPNExpressionPattern(String num, List<String> list, char[] expression, boolean[] flag, int numCount, int operatorCount) {
        // 数値と演算子が全て決まったかどうか
        if(numCount + operatorCount == EXPRESSION_LENGTH) {
            list.add(new String(expression));
        }

        // 演算子を入れることができるか
        // 演算子の数 = 数値の数 - 1のため、差が2以上あれば演算子を入れる余地がある
        if(2 <= numCount - operatorCount) {
            OPERATORS.forEach(c-> {
                expression[numCount + operatorCount] = c;
                createRPNExpressionPattern(num, list, expression, flag, numCount, operatorCount + 1);
            });
        }

        if(numCount < OPERAND_LENGTH) {
            for(int i = 0; i < OPERAND_LENGTH; i++) {
                if(!flag[i]) {
                    flag[i] = true;
                    expression[numCount + operatorCount] = num.toCharArray()[i];
                    createRPNExpressionPattern(num, list, expression, flag, numCount + 1, operatorCount);
                    flag[i] = false;
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
        if(num == null || num.length() != OPERAND_LENGTH || !num.matches("^\\d+$")) {
            throw  new IllegalArgumentException("num is illegal format.");
        }

        List<String> list = new ArrayList<>();

        // boolean の初期値はfalse
        createRPNExpressionPattern(num, list, new char[EXPRESSION_LENGTH], new boolean[OPERAND_LENGTH], 0, 0);
        // list は可変なので状態が変更されている
        return list;
    }
}
