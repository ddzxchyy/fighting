package cn.jzq.generalold.module.se;

import lombok.Data;

@Data
public class Animal {

    private String name;

    private String run() {
        return getName() + " run";
    }
}
