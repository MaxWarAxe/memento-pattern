package com.example.lr3aps;

import javafx.scene.shape.Shape;

import java.util.List;

public class Memento {
    private final List<Shape> shapes;

    Memento(List<Shape> shapes) {
        this.shapes = shapes;
    }
    public List<Shape> getState() {
        return shapes;
    }


}
