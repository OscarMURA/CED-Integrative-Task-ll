package com.example.romeandvikings.model;

import com.example.romeandvikings.controller.GameController;
import com.example.romeandvikings.exceptions.exceptionNoVertexExist;
import com.example.romeandvikings.exceptions.exceptionOnGraphTypeNotAllowed;
import com.example.romeandvikings.structures.*;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Random;

public class Map {

    private IGraph<Integer, City> map;
    private HashMap<Integer, RadioButton> radioButtons;
    private HashMap<Integer, Line> lines;

    public Map(Implementation implementationEnum) throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed {
        if (implementationEnum == Implementation.ADJACENCY_LIST) {
            map = new GraphAdjacentList<>(GraphType.SIMPLE);
        }else{
            map = new GraphAdjacentMatrix<>(50, GraphType.SIMPLE);
        }
        radioButtons = new HashMap<>();
        lines = new HashMap<>();
        addCities();
        addRoutes();
    }

    public void addCities() throws exceptionNoVertexExist {

        int[] positionsLevel0 = {20, 80, 150, 214, 320, 430, 510, 550,   540, 560, 520, 540, 560, 520, 540, 580};
        int[] positionsLevel1 = {50, 120, 200, 323, 387, 500, 560, 620,   488, 450, 468, 488, 508, 468, 488, 508};
        int[] positionsLevel2 = {200, 260, 320, 378, 440, 500, 560, 620};
        int[] positionsLevel3 = {170, 230, 280, 340, 398, 460, 520, 580, 650};
        int[] positionsLevel4 = {145, 205, 245, 350, 420, 490, 560, 615};
        int[] positionsLevel5 = {200, 240, 340, 400, 440, 500, 560} ;

        City rome = new City((400), (600), 0);
        map.addVertex(rome.getId(), rome);
        RadioButton radioButton1 = new RadioButton();
        radioButton1.setStyle("-fx-background-color: #FF0000;");
        radioButton1.setLayoutX(rome.getX());
        radioButton1.setLayoutY(rome.getY());
        radioButton1.setCursor(Cursor.HAND);
        radioButton1.setSelected(true);
        radioButton1.setDisable(true);
        radioButtons.put(rome.getId(), radioButton1);

        City vikings = new City(470, 100, 49);
        map.addVertex(vikings.getId(), vikings);
        RadioButton radioButton2 = new RadioButton();
        radioButton2.setLayoutX(vikings.getX());
        radioButton2.setLayoutY(vikings.getY());
        radioButton2.setCursor(Cursor.HAND);
        radioButtons.put(vikings.getId(), radioButton2);

        for (int i = 1; i < 9; i++) {
            City city = new City(positionsLevel0[i-1], positionsLevel0[i+7], i);
            map.addVertex(city.getId(), city);
            RadioButton radioButton3 = new RadioButton();
            radioButton3.setLayoutX(city.getX());
            radioButton3.setLayoutY(city.getY());
            radioButton3.setCursor(Cursor.HAND);
            radioButtons.put(city.getId(), radioButton3);

        }

        int j = 0;
        for (int i = 9; i < 17; i++) {
            City city = new City(positionsLevel1[j], positionsLevel1[j+8], i);
            map.addVertex(city.getId(), city);
            RadioButton radioButton4 = new RadioButton();
            radioButton4.setLayoutX(city.getX());
            radioButton4.setLayoutY(city.getY());
            radioButton4.setCursor(Cursor.HAND);
            radioButtons.put(city.getId(), radioButton4);
            j++;
        }

        for (int i = 17; i < 25; i++) {
            City city = new City(positionsLevel2[i-17], (420), i);
            map.addVertex(city.getId(), city);
            RadioButton radioButton5 = new RadioButton();
            radioButton5.setLayoutX(city.getX());
            radioButton5.setLayoutY(city.getY());
            radioButton5.setCursor(Cursor.HAND);
            radioButtons.put(city.getId(), radioButton5);
        }

        for (int i = 25; i < 34; i++) {
            City city = new City(positionsLevel3[i-25], (360), i);
            map.addVertex(city.getId(), city);
            RadioButton radioButton6 = new RadioButton();
            radioButton6.setLayoutX(city.getX());
            radioButton6.setLayoutY(city.getY());
            radioButton6.setCursor(Cursor.HAND);
            radioButtons.put(city.getId(), radioButton6);

        }

        for (int i = 34; i < 42; i++) {
            City city = new City(positionsLevel4[i-34], (280), i);
            map.addVertex(city.getId(), city);
            RadioButton radioButton7 = new RadioButton();
            radioButton7.setLayoutX(city.getX());
            radioButton7.setLayoutY(city.getY());
            radioButton7.setCursor(Cursor.HAND);
            radioButtons.put(city.getId(), radioButton7);
        }
        for (int i = 42; i < 49; i++) {
            City city = new City(positionsLevel5[i-42], (210), i);
            map.addVertex(city.getId(), city);
            RadioButton radioButton8 = new RadioButton();
            radioButton8.setLayoutX(city.getX());
            radioButton8.setLayoutY(city.getY());
            radioButton8.setCursor(Cursor.HAND);
            radioButtons.put(city.getId(), radioButton8);
        }

    }

    public void addRoutes() throws exceptionNoVertexExist, exceptionOnGraphTypeNotAllowed {
        Random random = new Random();

        Route[] routes = new Route[57]; //57

        //Todo bien hasta aqui primera zona 1 - 8

        routes[0] = new Route(6, 0, random.nextInt(20) + 1);
        routes[1] = new Route(0, 7, random.nextInt(20) + 1);
        routes[2] = new Route(0, 2, random.nextInt(20) + 1);
        routes[3] = new Route(49, 45, random.nextInt(20) + 1);
        routes[4] = new Route(49, 46, random.nextInt(20) + 1);
        routes[5] = new Route(49, 48, random.nextInt(20) + 1);
        routes[6] = new Route(2, 1, random.nextInt(20) + 1);
        routes[7] = new Route(2, 3, random.nextInt(20) + 1);
        routes[8] = new Route(5, 4, random.nextInt(20) + 1);
        routes[9] = new Route(5, 6, random.nextInt(20) + 1);
        routes[10] = new Route(46, 47, random.nextInt(20) + 1);

        //segunda parte 9 - 15
        routes[11] = new Route(8, 16, random.nextInt(20) + 1);
        routes[12] = new Route(1, 9, random.nextInt(20) + 1);
        routes[13] = new Route(3, 10, random.nextInt(20) + 1);
        routes[14] = new Route(4, 11, random.nextInt(20) + 1);
        routes[15] = new Route(6, 13, random.nextInt(20) + 1);
        routes[16] = new Route(7, 14, random.nextInt(20) + 1);
        routes[17] = new Route(7, 15, random.nextInt(20) + 1);
        routes[18] = new Route(7, 16, random.nextInt(20) + 1);
        routes[19] = new Route(13, 12, random.nextInt(20) + 1);

        //tercera

        routes[20] = new Route(10, 17, random.nextInt(20) + 1);
        routes[21] = new Route(11, 18, random.nextInt(20) + 1);
        routes[22] = new Route(12, 19, random.nextInt(20) + 1);
        routes[23] = new Route(12, 20, random.nextInt(20) + 1);
        routes[24] = new Route(14, 21, random.nextInt(20) + 1);
        routes[25] = new Route(15, 22, random.nextInt(20) + 1);
        routes[26] = new Route(16, 23, random.nextInt(20) + 1);
        routes[27] = new Route(16, 24, random.nextInt(20) + 1);


        //cuarta

        routes[28] = new Route(17, 25, random.nextInt(20) + 1);
        routes[29] = new Route(18, 26, random.nextInt(20) + 1);
        routes[30] = new Route(19, 27, random.nextInt(20) + 1);
        routes[31] = new Route(20, 28, random.nextInt(20) + 1);
        routes[32] = new Route(21, 29, random.nextInt(20) + 1);
        routes[33] = new Route(22, 30, random.nextInt(20) + 1);
        routes[34] = new Route(23, 31, random.nextInt(20) + 1);
        routes[35] = new Route(24, 32, random.nextInt(20) + 1);
        routes[36] = new Route(24, 33, random.nextInt(20) + 1);

        //quinta

        routes[37] = new Route(25, 34, random.nextInt(20) + 1);
        routes[38] = new Route(26, 35, random.nextInt(20) + 1);
        routes[39] = new Route(27, 36, random.nextInt(20) + 1);
        routes[40] = new Route(28, 37, random.nextInt(20) + 1);
        routes[41] = new Route(29, 38, random.nextInt(20) + 1);
        routes[42] = new Route(30, 39, random.nextInt(20) + 1);
        routes[43] = new Route(31, 40, random.nextInt(20) + 1);
        routes[44] = new Route(32, 41, random.nextInt(20) + 1);
        routes[45] = new Route(33, 41, random.nextInt(20) + 1);


        //sexta

        routes[46] = new Route(34, 43, random.nextInt(20) + 1);
        routes[47] = new Route(35, 43, random.nextInt(20) + 1);
        routes[48] = new Route(36, 43, random.nextInt(20) + 1);
        routes[49] = new Route(37, 45, random.nextInt(20) + 1);
        routes[50] = new Route(38, 45, random.nextInt(20) + 1);
        routes[51] = new Route(47, 39, random.nextInt(20) + 1);

        //union sexta con primera

        routes[52] = new Route(40, 48, random.nextInt(20) + 1);
        routes[53] = new Route(41, 40, random.nextInt(20) + 1);
        routes[54] = new Route(44, 45, random.nextInt(20) + 1);
        routes[55] = new Route(43, 42, random.nextInt(20) + 1);
        routes[56] = new Route(43, 44, random.nextInt(20) + 1);


        for (Route route : routes) {
            map.addEdge(route.getCityA(), route.getCityB(), route.getDifficulty());

            RadioButton city1 = radioButtons.get(route.getCityA());
            RadioButton city2 = radioButtons.get(route.getCityB());

            Line line = new Line(city1.getLayoutX() + 8, city1.getLayoutY() + 8, city2.getLayoutX() + 8, city2.getLayoutY() + 8);

            line.setCursor(Cursor.HAND);
            String tooltipText = String.valueOf(route.getDifficulty());
            Tooltip tooltip = new Tooltip(tooltipText);
            Tooltip.install(line, tooltip);
            line.setStroke(Color.BLACK);

            line.setStrokeWidth(1.5);
            lines.put(route.getCityA() + route.getCityB(), line);
        }

    }

    public IGraph<Integer, City> getGraph() {
        return map;
    }

    public HashMap<Integer, RadioButton> getRadioButtons() {
        return radioButtons;
    }

    public HashMap<Integer, Line> getLines() {
        return lines;
    }




}
