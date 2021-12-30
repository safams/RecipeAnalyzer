package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ingredient extends Connector {

    private int id;
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public void setID() {
        try {
            ResultSet rs = query.executeQuery("SELECT id FROM Ingredient WHERE name = '" + this.name + "'");
            while (rs.next()) {
                Integer result = Integer.parseInt(rs.getString("id"));
                this.id = result;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public String getIngName() throws SQLException {
        ResultSet rs = query.executeQuery("SELECT name FROM Ingredient WHERE id = " + this.id);
        String ingName = null;
        while (rs.next()) {
            String result = rs.getString("name");
            ingName = result;
        }
        return ingName;
    }



    public void addToDatabase() {
        try {
            query.executeUpdate("INSERT INTO Ingredient" + "(name)" + "VALUES('" + name + "')");
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

}
