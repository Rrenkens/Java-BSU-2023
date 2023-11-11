package by.N3vajnoKto.quizer.task_generators;

import by.N3vajnoKto.quizer.Task;
import by.N3vajnoKto.quizer.tasks.math_tasks.ExpressionTask;

import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.util.Random;

public class CircleGenerator implements Task.Generator {
    private double min;
    private double max;

    private int precision;
    Random rnd;

    public CircleGenerator(double min, double max, int precision) {
        this.min = min;
        this.max = max;
        this.rnd = new Random();
        this.precision = precision;
    }

    public String format(double a, int n) {
        if (n < 0) {
            throw new InvalidParameterException("precision is below zero");
        }
        DecimalFormat format = new DecimalFormat();
        format.setMaximumFractionDigits(this.precision);
        if (a > 0) {
            return format.format(a);
        } else {
            return "(" + format.format(a) + ")";
        }
    }

    static public double round(double a, int n) {
        if (n > 9) {
            throw new InvalidParameterException("precision is too high");
        }

        if (n < 0) {
            throw new InvalidParameterException("precision is below zero");
        }

        a *= Math.pow(10, n);
        a = Math.round(a);
        a /= Math.pow(10, n);
        return a;
    }

    @Override
    public Task generate() {
        double r = round(rnd.nextDouble(this.min, this.max), this.precision);
        return new ExpressionTask("print the square of circle with radius " + format(r, this.precision) + " (precision: " + this.precision + ")", round(r * r * Math.PI, this.precision), this.precision);
    }
}