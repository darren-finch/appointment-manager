package com.darrenfinch.appointmentmanager.common.utils.formatters;

import javafx.scene.control.TextFormatter;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public abstract class BaseFormatterOperator implements UnaryOperator<TextFormatter.Change> {
    @Override
    public <V> Function<V, TextFormatter.Change> compose(Function<? super V, ? extends TextFormatter.Change> before) {
        return UnaryOperator.super.compose(before);
    }

    @Override
    public <V> Function<TextFormatter.Change, V> andThen(Function<? super TextFormatter.Change, ? extends V> after) {
        return UnaryOperator.super.andThen(after);
    }
}
