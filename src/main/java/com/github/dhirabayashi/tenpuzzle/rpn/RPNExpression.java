package com.github.dhirabayashi.tenpuzzle.rpn;

import com.github.dhirabayashi.tenpuzzle.data.Num;
import com.github.dhirabayashi.tenpuzzle.data.Operator;
import com.github.dhirabayashi.tenpuzzle.data.Rational;
import com.github.dhirabayashi.tenpuzzle.data.SyntaxTree;

import java.util.LinkedList;
import java.util.Optional;

/**
 * 逆ポーランド記法の式
 */
public class RPNExpression {
    private SyntaxTree<Rational> syntaxTree;

    /**
     * 逆ポーランド記法の式を指定してRPNExpression を構築する。
     * @param expression 逆ポーランド記法の式。式は0〜9までの数字、'+', '-', '*', '/'の演算子のみで構成され、区切り文字なしの形式である必要がある。
     * @throws IllegalArgumentException expression の形式が不正な場合
     */
    public RPNExpression(String expression) {
        LinkedList<Character> stack = new LinkedList<>();
        for(char c : expression.toCharArray()) {
            stack.push(c);
        }

        syntaxTree = createTree(stack);

        if(!stack.isEmpty() || !(syntaxTree instanceof Operator)) {
            throw new IllegalArgumentException("expression is illegal format.");
        }
    }

    // 逆ポーランド記法の式を逆から（スタックの取り出し順）から解析していけばちょうど構文木になる
    private SyntaxTree<Rational> createTree(LinkedList<Character> stack) {
        if(stack.isEmpty()) {
            return null;
        }

        Character c = stack.pop();
        if(Character.isDigit(c)) {
            return new Num(new Rational(Character.digit(c, 10)));
        } else {
            // 右が先
            SyntaxTree<Rational> right = createTree(stack);
            SyntaxTree<Rational> left = createTree(stack);

            if(right == null || left == null) {
                throw new IllegalArgumentException("expression is illegal format.");
            }

            return new Operator(c, left, right);
        }
    }

    /**
     * この式を表す中置記法の式を返す
     * @return 中置記法の式
     */
    public String toInfix() {
        String tmp = syntaxTree.toString();
        return tmp.substring(1, tmp.length() - 1);
    }

    /**
     * 式を評価する。
     * @return 評価された値。評価できない場合は空のOptional
     */
    public Optional<Rational> eval() {
        return syntaxTree.eval();
    }
}
