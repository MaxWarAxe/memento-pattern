package com.example.lr3aps;

import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    List<Memento> mementoList = new ArrayList<>();

    public void addMemento(List<Shape> shapes) {
        Memento newMemento = new Memento(shapes);
        mementoList.add(newMemento);
    }

    public List<Shape> getMemento() {
        return mementoList.get(mementoList.size() - 1).getState();
    }
}
