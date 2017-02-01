package hu.vk.model;

import lombok.Data;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@Data
public class Point {

    private Integer x;

    private Integer y;

    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public Point() {
    }
}
