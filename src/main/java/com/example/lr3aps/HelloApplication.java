package com.example.lr3aps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    Pane root = new Pane();
    List<Shape> shapes = new ArrayList<>();
    private double startX, startY; // Начальные координаты мыши
    private double startWidth, startHeight; // Начальные размеры фигуры
    private double shapeStartX, shapeStartY; // Начальные координаты фигуры
    Caretaker caretaker = new Caretaker();

    private void save(){
        caretaker.addMemento(copyShapes(shapes));
    }

    private void load(){
        root.getChildren().clear();
        shapes.clear();
        shapes.addAll(caretaker.getMemento());
        root.getChildren().addAll(shapes);
    }

    @Override
    public void start(Stage primaryStage) {
        Button button = new Button("Добавить круг");
        button.setOnMouseClicked(event -> {addCircle();});
        Button button2 = new Button("Добавить прямоугольник");
        button2.setOnMouseClicked(event -> {addRectangle();});
        Button button3 = new Button("Save");
        button3.setOnMouseClicked(event->{save();});
        Button button4 = new Button("Load");
        button4.setOnMouseClicked(event->{load();});
        HBox hbox = new HBox(button, button2,button3,button4);

        Pane pane = new Pane();
        VBox vBox = new VBox(hbox, root);

        pane.getChildren().add(vBox);

        Scene scene = new Scene(pane, 1280, 720);

        primaryStage.setTitle("Редактор фигур");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Обработка нажатия мыши
    private void handleMousePressed(MouseEvent event, Object shape) {
        startX = event.getSceneX();
        startY = event.getSceneY();

        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            startWidth = rect.getWidth();
            startHeight = rect.getHeight();
            shapeStartX = rect.getX();
            shapeStartY = rect.getY();
        } else if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            startWidth = circle.getRadius() * 2; // Диаметр круга
            startHeight = startWidth;
            shapeStartX = circle.getCenterX();
            shapeStartY = circle.getCenterY();
        }
    }

    // Обработка перетаскивания мыши
    private void handleMouseDragged(MouseEvent event, Object shape) {
        double deltaX = event.getSceneX() - startX;
        double deltaY = event.getSceneY() - startY;

        if (event.isShiftDown()) {
            // Если зажата клавиша Shift, изменяем размер фигуры
            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                rect.setWidth(Math.max(10, startWidth + deltaX)); // Минимальная ширина 10
                rect.setHeight(Math.max(10, startHeight + deltaY)); // Минимальная высота 10
            } else if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                double newRadius = Math.max(10, (startWidth + deltaX) / 2); // Минимальный радиус 10
                circle.setRadius(newRadius);
            }
        } else {
            // Если Shift не зажат, перемещаем фигуру
            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                rect.setX(shapeStartX + deltaX);
                rect.setY(shapeStartY + deltaY);
            } else if (shape instanceof Circle) {
                Circle circle = (Circle) shape;
                circle.setCenterX(shapeStartX + deltaX);
                circle.setCenterY(shapeStartY + deltaY);
            }
        }
    }

    private void addCircle(){
        Shape circle = createCircle(200, 200, 50);
        shapes.add(circle);
        root.getChildren().add(circle);
    }

    private void addRectangle(){
        Shape rectangle = createRectangle(50,50,100,80);
        shapes.add(rectangle);
        root.getChildren().add(rectangle);
    }

    private Shape createRectangle(double x, double y, double width, double height){
        Shape rectangle = new Rectangle(x,y,width,height);
        rectangle.setFill(Color.LIGHTBLUE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setOnMousePressed(event -> handleMousePressed(event, rectangle));
        rectangle.setOnMouseDragged(event -> handleMouseDragged(event, rectangle));
        return rectangle;
    }
    private Shape createCircle(double x, double y, double radius){
        Shape circle = new Circle(x,y,radius);
        circle.setFill(Color.LIGHTCORAL);
        circle.setStroke(Color.BLACK);
        circle.setOnMousePressed(event -> handleMousePressed(event, circle));
        circle.setOnMouseDragged(event -> handleMouseDragged(event, circle));
        return circle;
    }

    private List<Shape> copyShapes(List<Shape> shapes){
        List<Shape> shapes1 = new ArrayList<>();
        for(Shape shape : shapes){
            shapes1.add(copyShape(shape));
        }
        return shapes1;
    }

    private Shape copyShape(Shape shape){
        Shape newShape = null;
        if(shape instanceof Rectangle){
            newShape = createRectangle(
                    ((Rectangle) shape).getX(),
                    ((Rectangle) shape).getY(),
                    ((Rectangle) shape).getWidth(),
                    ((Rectangle) shape).getHeight()
            );
            newShape.setFill(shape.getFill());
            newShape.setStroke(shape.getStroke());
        }
        if(shape instanceof Circle){
            newShape = createCircle(
                    ((Circle) shape).getCenterX(),
                    ((Circle) shape).getCenterY(),
                    ((Circle) shape).getRadius()
            );
            newShape.setFill(shape.getFill());
            newShape.setStroke(shape.getStroke());
        }
        return newShape;
    }

    public static void main(String[] args) {
        launch(args);
    }
}