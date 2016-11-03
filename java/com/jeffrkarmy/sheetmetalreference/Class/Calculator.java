package com.jeffrkarmy.sheetmetalreference.Class;

/**
 * Created by jrkar on 7/13/2016.
 */
public class Calculator {

    /**
     * calculates a bend allowance.
     *
     * @param mt Thickness of material.
     * @param r  Inside radios of bend.
     * @param a  Angle of bend 1 - 180.
     * @return bend allowance.
     */
    public double empiricalBendAllowanceFormula(double mt, double r, double a) {
        return (.01743 * r + .0078 * mt) * a;
    }

    /**
     * Calculates the bend deduction of a bend, uses bend allowance and an angle.
     *
     * @param ba Bend allowance.
     * @param r  Inside radios of bend.
     * @param mt Thickness of material.
     * @param a  Angle of bend 1 - 180.
     * @return bend deduction.
     */
    public double bendDeduction(double ba, double r, double mt, double a) {
        return 2 * (r + mt) * Math.tan(Math.toRadians(a / 2)) - ba;
    }

    /**
     * @param bd  Bend deduction
     * @param leg length of the leg
     * @return length of the flat, leg - half a bend deduction.
     */
    public double calculateFlat(double bd, double leg) {
        return leg - (bd / 2);
    }

    /**
     * Calculates the bend deduction of a 90 degree bend.
     *
     * @param mt Thickness of material.
     * @param r  Inside radios of bend
     * @return bend deduction.
     */
    public double nintyOnlyBendDeduction(double mt, double r) {
        return (3 * mt + r) * .4343;
    }


    /**
     * Check to see if a value is greater than 0.
     *
     * @param value
     * @return Boolean
     */
    public boolean greaterThanZero(double value) {
        return value <= 0;

    }

    /**
     * Check that angle is greater than 0 and less or equal to 180.
     *
     * @param angle
     * @return Boolean
     */
    public boolean checkAngle(double angle) {
        if (angle <= 0) {
            return true;
        } else return angle > 180;

    }


}
