package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import java.util.ArrayList;


public class Map {

    private String name;
    private ArrayList<Planet> planets;
//private Image backgroundImage = someImage;?
    public Map(String name, ArrayList<Planet> planets){
        this.name = name;
        this.planets = planets;
    }

    public String getName(){
        return name;
    }

    //TODO: read method
    //TODO: draw method

}
