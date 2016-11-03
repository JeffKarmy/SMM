package com.jeffrkarmy.sheetmetalreference.Class;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jrkar on 8/4/2016.
 */
public final class HistoryDB {

    private int _id;
    private double _materialThickness;
    private double _radius;
    private double _angle;
    private double _bendDeduction;
    private String _date;
    private String _description;

    public HistoryDB() {
    }

    public HistoryDB(double materialThickness, double radius, double angle, double bendDeduction) {
        this._materialThickness = materialThickness;
        this._radius = radius;
        this._angle = angle;
        this._bendDeduction = bendDeduction;

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yyyy");

        this._date = dateFormat.format(date);
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public double getMaterialThickness() {
        return this._materialThickness;
    }

    public void setMaterialThickness(double mt) {
        this._materialThickness = mt;
    }

    public double getRadius() {
        return this._radius;
    }

    public void setRadius(double radius) {
        this._radius = radius;
    }

    public double getAngle() {
        return this._angle;
    }

    public void setAngle(double angle) {
        this._angle = angle;
    }

    public double getBendDeduction() {
        return this._bendDeduction;
    }

    public void setBendDeduction(double bd) {
        this._bendDeduction = bd;
    }

    public String getDate() {
        return this._date;
    }

    public void setDate(String date) {
        this._date = date;
    }

    public String getDescription() {
        return this._description;
    }

    public void setDescription(String description) {
        this._description = description;
    }

}