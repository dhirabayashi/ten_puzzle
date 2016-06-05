package com.github.dhirabayashi.tenpuzzle.data;

import java.util.Optional;

/**
 * 構文木
 */
public interface SyntaxTree<T> {

    /**
     * 構文を評価する
     * @return 評価された値。評価できない場合は空のOptional
     */
    Optional<T> eval();
}
