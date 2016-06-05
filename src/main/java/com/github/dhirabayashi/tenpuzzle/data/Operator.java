package com.github.dhirabayashi.tenpuzzle.data;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * 構文木の演算子。必ず2つの子を持つ。
 */
public class Operator implements SyntaxTree<Rational> {

    private SyntaxTree<Rational> left;
    private SyntaxTree<Rational> right;
    private char c;
    private Supplier<Optional<Rational>> func;

    /**
     * 演算子文字と子要素を指定してOperatorを構築する
     * @param c 演算子文字。'+', '-', '*', '/'のいずれか
     * @param left 左の子要素
     * @param right 右の子要素
     * @throws IllegalArgumentException 演算子文字が不正な場合
     */
    public Operator(char c, SyntaxTree<Rational> left, SyntaxTree<Rational> right) {

        if(left == null || right == null) {
            throw new NullPointerException();
        }

        this.c = c;
        this.left = left;
        this.right = right;

        switch (c) {
            case '+' :
                func = () -> left.eval().flatMap(l -> right.eval().map(l::add));
                break;
            case '-' :
                func = () -> left.eval().flatMap(l -> right.eval().map(l::subtract));
                break;
            case '*' :
                func = () -> left.eval().flatMap(l -> right.eval().map(l::multiply));
                break;
            case '/' :
                // 割り算はゼロ除算回避が必要
                func = () -> left.eval().flatMap(l -> right.eval().filter(r -> r.getNumerator() != 0).map(l::divide));
                break;
            default:
                throw new IllegalArgumentException("illegal operator : " + c);
        }
    }

    public Optional<Rational> eval() {
        return func.get();
    }

    @Override
    public String toString() {
        return String.format("(%s%s%s)", left.toString(), String.valueOf(c), right.toString());
    }
}
