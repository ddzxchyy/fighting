package cn.jzq.designpattern.creating.factory.simple;

import java.util.function.Supplier;

public enum CarType {
    HONDA(Honda::new),
    Tesla(Tesla::new);

    private final Supplier<Car> constructor;

    CarType(Supplier<Car> constructor) {
        this.constructor = constructor;
    }

    public Supplier<Car> getConstructor() {
        return constructor;
    }
}
