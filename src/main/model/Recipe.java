package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Recipe extends Connector {

    private int id;
    private String name;
    private double time;
    private List ingredients;

    public Recipe(String name, Double time) {
        this.name = name;
        this.time = time;
        ingredients = new ArrayList();
    }

    public void setID() {
        try {
            ResultSet rs = query.executeQuery("SELECT id FROM Recipe WHERE name = '" + this.name + "'");
            while (rs.next()) {
                Integer result = Integer.parseInt(rs.getString("id"));
                this.id = result;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Double setTime() {
        try {
            ResultSet rs = query.executeQuery("SELECT cook_time FROM Recipe WHERE name = '" + this.name + "'");
            while (rs.next()) {
                Double result = Double.parseDouble(rs.getString("cook_time"));
                this.time = result;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return time;

    }

    public String getRecName() throws SQLException {
        ResultSet rs = query.executeQuery("SELECT name FROM Recipe WHERE id = " + this.id);
        String recName = null;
        while (rs.next()) {
            String result = rs.getString("name");
            recName = result;
        }
        return recName;
    }

    public void addToDatabase() {
        try {
            query.executeUpdate("INSERT INTO Recipe" + "(name, cook_time)" + "VALUES('" + name + "', '" + time + "')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getTime() {
        return time;
    }













}
