package com.jeffrkarmy.sheetmetalreference.Class;

/**
 * Custom template
 * Created by jrkar on 8/26/2016.
 */
final class AlloyOjb {

    private int _alloy;
    private String _temper;
    private String _thickness;
    private String _radius;

    AlloyOjb() {
    }

    public AlloyOjb(int alloy, String temper, String thickness, String radius) {
        this._alloy = alloy;
        this._temper = temper;
        this._thickness = thickness;
        this._radius = radius;
    }

    int getAlloy() {
        return this._alloy;
    }

    void setAlloy(int alloy) {
        this._alloy = alloy;
    }

    String getTemper() {
        return this._temper;
    }

    void setTemper(String temper) {
        this._temper = temper;
    }

    public String getThickness() {
        return this._thickness;
    }

    public void setThickness(String thickenss) {
        this._thickness = thickenss;
    }

    public String getRadius() {
        return this._radius;
    }

    public void setRadius(String radius) {
        this._radius = radius;
    }

}
