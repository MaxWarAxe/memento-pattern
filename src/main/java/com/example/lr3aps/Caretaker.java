package com.example.lr3aps;

import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    int currentMemento = -1;
    List<Memento> mementoList = new ArrayList<>();

    public void addMemento(List<Shape> shapes) {
        currentMemento++;
        Memento newMemento = new Memento(shapes);
        mementoList.add(newMemento);
    }

    public List<Shape> restore() {
        if(mementoList.isEmpty() || currentMemento == 0) return new ArrayList<Shape>();
        currentMemento--;
        return mementoList.get(currentMemento).getState();
    }

    public List<Shape> undoRestore() {
        if(mementoList.size()-1 == currentMemento) return  mementoList.get(currentMemento).getState();
        currentMemento++;
        return mementoList.get(currentMemento).getState();
    }

    public void cutToCurrent(){
        if(mementoList.isEmpty()) return;
        while (currentMemento + 1 < mementoList.size()) {
            popMemento();
        }
    }

    public void popMemento() {
        if(!mementoList.isEmpty()) {
            mementoList.remove(mementoList.size() - 1);
        }
    }
}
