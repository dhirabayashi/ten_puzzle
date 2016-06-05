package com.github.dhirabayashi.tenpuzzle.data;

/**
 * 有理数
 */
public final class Rational {

    /**
     * 分子
     */
    private int numerator;

    /**
     * 分母
     */
    private int denominator;

    /**
     * 分子と分母を指定してRationalを構築する。約分した状態で保持する。
     * @param numerator 分子
     * @param denominator 分母
     * @throws IllegalArgumentException denominator が0の場合
     */
    public Rational(int numerator, int denominator) {
        if(denominator == 0) {
            throw new IllegalArgumentException("denominator is zero");
        }

        int a = Math.abs(Math.max(numerator, denominator));
        int b = Math.abs(Math.min(numerator, denominator));

        int g = gcd(a, b);

        this.numerator = numerator / g;
        this.denominator = denominator / g;
    }

    /**
     * 分子を指定してRationalを構築する。分母には1が設定される。
     * @param numerator 分子
     */
    public Rational(int numerator) {
        this(numerator, 1);
    }

    // ユークリッドの互除法で最大公約数を求める
    private int gcd(int a, int b) {
        if(b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    /**
     * 分子を取得する
     * @return 分子
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * 分母を取得する
     * @return 分母
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * 分数の加算を行う
     * @param that 加算する分数
     * @return 計算結果の分数
     */
    public Rational add(Rational that) {
        // お互いの分母で通分して加算
        int numerator = this.numerator * that.getDenominator() + that.getNumerator() * this.denominator;
        int denominator = this.denominator * that.getDenominator();
        return new Rational(numerator, denominator);
    }

    /**
     * 分数の減算を行う
     * @param that 減算する分数
     * @return 計算結果の分数
     */
    public Rational subtract(Rational that) {
        // お互いの分母で通分して減算
        int numerator = this.numerator * that.getDenominator() - that.getNumerator() * this.denominator;
        int denominator = this.denominator * that.getDenominator();
        return new Rational(numerator, denominator);
    }

    /**
     * 分数の乗算を行う
     * @param that 乗算する分数
     * @return 計算結果の分数
     */
    public Rational multiply(Rational that) {
        int numerator = this.numerator * that.getNumerator();
        int denominator = this.denominator * that.getDenominator();
        return new Rational(numerator, denominator);
    }

    /**
     * 分数の除算を行う
     * @param that 除算する分数
     * @return 計算結果の分数
     * @throws ArithmeticException thatの分子が0の場合
     */
    public Rational divide(Rational that) {
        if(that.getNumerator() == 0) {
            throw new ArithmeticException("numerator of divisor is 0.");
        }
        int numerator = this.numerator * that.getDenominator();
        int denominator = this.denominator * that.getNumerator();
        return new Rational(numerator, denominator);
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    /**
     * この分数の小数値を返す。
     * @return この分数の小数値
     */
    public double doubleValue() {
        return (double)numerator / (double)denominator;
    }
}
