package cn.jzq.designpattern.creating.factory.simple;

/**
 * 简单工厂模式
 */
public class SimpleCarFactory {

    public static Car createCar(CarType carType){
        return carType.getConstructor().get();
    }
}
