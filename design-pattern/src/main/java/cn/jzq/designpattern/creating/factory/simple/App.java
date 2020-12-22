package cn.jzq.designpattern.creating.factory.simple;

public class App {

    public static void main(String[] args) {
        Car tesla = SimpleCarFactory.createCar(CarType.Tesla);
        System.out.println(tesla.getBrand());
        Car honda = SimpleCarFactory.createCar(CarType.HONDA);
        System.out.println(honda.getBrand());
    }
}
