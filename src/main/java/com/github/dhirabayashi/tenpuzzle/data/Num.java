package com.github.dhirabayashi.tenpuzzle.data;

import java.util.Optional;

/**
 * 構文木の数値。子を持たない。
 */
public class Num implements SyntaxTree<Rational> {

    private Rational value;

    /**
     * Rational を指定してNumを構築する
     * @param value 有理数
     */
    public Num(Rational value) {
        this.value = value;
    }

    @Override
    public Optional<Rational> eval() {
        return Optional.of(value);
    }

    /**
     * 値の文字列表現を返す
     * @return 値の文字列表現
     */
    @Override
    public String toString() {
        int d = value.getDenominator();
        return d == 1 ?  String.valueOf(value.getNumerator()) : String.valueOf(value);
    }
}
