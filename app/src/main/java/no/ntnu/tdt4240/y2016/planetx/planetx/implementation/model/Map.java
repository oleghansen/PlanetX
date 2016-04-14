package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import java.util.ArrayList;

/**
 * Created by Ole on 31.03.2016.
 */
public class Map {

    private String name;
    private ArrayList<Planet> planets;

    public Map(String name, ArrayList<Planet> planets){
        this.name = name;
        this.planets = planets;
    }

    public String getName(){
        return name;
    }
}
