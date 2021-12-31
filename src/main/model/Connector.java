package main.model;

import java.sql.*;

public abstract class Connector {

    protected Connection connect;
    protected Statement query;

    public Connector() {
        try {
            createConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected Statement createConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connect = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/recipeDatabase", "root", "");

        query = connect.createStatement();

        return query;
    }

    protected ResultSet printRecipeDetails(int id) throws SQLException {
        ResultSet rs = query.executeQuery("SELECT i.name\n" +
                "FROM Recipe AS r\n" +
                "INNER JOIN Relationship AS ri ON r.id = ri.recipe_id\n" +
                "INNER JOIN Ingredient AS i ON i.id = ri.ingredient_id\n" +
                "WHERE r.id = " + id + ";");
        return rs;
    }





//    protected void printRecipeIngredients() throws SQLException {
//        ResultSet rs = query.executeQuery("SELECT r.id, i.id\n" +
//                "FROM Recipes AS r\n" +
//                "INNER JOIN Relationship AS ri ON r.id = ri.recipe_id\n" +
//                "INNER JOIN Ingredients AS i ON i.id = ri.ingredient_id\n" +
//                "GROUP BY r.id\n");
//        while (rs.next()) {
//            Integer idVal = Integer.parseInt(rs.getString("id"));
//            System.out.println(nameFromID(idVal));
//            System.out.println(name + "\n "  + time);
        }
//    }




