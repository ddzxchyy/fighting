package cn.jzq.generalold.module.se;

public class Dog extends Animal {


    public static void main(String[] args) {
        Animal dog = new Dog();
        dog.setName("dog");
        test(dog);
        System.out.println(dog);
    }

    public static void test(Animal animal) {
        animal.setName("duck");
        animal = new Animal();
        animal.setName("cat");
    }

}
