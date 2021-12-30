package main.ui;

import main.model.Connector;
import main.model.Ingredient;
import main.model.Recipe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataBase extends Connector {

    private Boolean run = true;
    private Scanner input;
    String command = null;
    private static final String VIEW_INGREDIENTS_COMMAND = "1";
    private static final String VIEW_RECIPES_COMMAND = "2";
    private static final String ADD_INGREDIENT_COMMAND = "3";
    private static final String ADD_RECIPE_COMMAND = "4";
    private static final String VIEW_COMMAND = "view";
    private static final String MAIN_MENU_COMMAND = "menu";
    private static final String QUIT_COMMAND = "quit";

    public DataBase() {
        runDatabase();
    }

    private void runDatabase() {
        input = new Scanner(System.in);

        while (run) {
            printInstructions();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals(QUIT_COMMAND)) {
                run = false;
            } else {
                commandOptions(command);
            }
        }

        System.out.println("Thank you for using Recipe Analyzer!");




    }

    private void invalid() {
        System.out.println("Selection not valid.... \n" +
                "Taking you back to main menu!");
        runDatabase();
    }

    private void commandOptions(String command) {

        switch (command) {
            case VIEW_INGREDIENTS_COMMAND:
                viewIngredients();
                break;
            case VIEW_RECIPES_COMMAND:
                viewRecipes();
                break;
            case ADD_INGREDIENT_COMMAND:
                addIngredients();
                break;
            case ADD_RECIPE_COMMAND:
                addRecipes();
                break;
            default:
                invalid();
        }


    }

    private void addRecipes() {
        System.out.println("Type in the name of the recipe you'd like to add");
        input = new Scanner(System.in);
        String name = input.nextLine();
        System.out.println("Type in the ingredients for "+ name);
        input = new Scanner(System.in);
        String ingredientName = input.nextLine();

//        Recipe recipe = new Recipe(name);
    }

    private void addIngredients() {
        System.out.println("Type in the name of the ingredient you'd like to add");
        input = new Scanner(System.in);
        String name = input.nextLine();
        Ingredient ingredient = new Ingredient(name);
        ingredient.addToDatabase();
        System.out.println(name +" has been successfully added to the ingredient database!");
    }

    private void viewRecipes() {
        System.out.println("Recipes:\n");
        try {
            printTimes();
            expandOptions();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    private void expandOptions() throws SQLException {
        System.out.println("\nTo see a recipe's details, type in it's name.\n");
        Scanner selection = new Scanner(System.in);
        String name = selection.nextLine();
        Recipe choice = new Recipe(name, 1.0);
        choice.setID();
        Integer choiceID = choice.getId();

        System.out.println("\nIngredients:\n");


        ResultSet rs = printRecipeDetails(choiceID);
        while (rs.next()) {
            String result = rs.getString("name");
            System.out.println(result);
        }

        System.out.println("\nCooking Time: "+ choice.setTime() + " min");

        System.out.println("\n Would you like to:\n view more recipes (view),\n " +
                "quit the program (quit),\n or return to main menu (menu)?");
        Scanner choose = new Scanner(System.in);

        switch (choose.nextLine()) {
            case QUIT_COMMAND:
                run = false;
                break;
            case MAIN_MENU_COMMAND:
                runDatabase();
                break;
            case VIEW_COMMAND:
                viewRecipes();
                break;
            default:
                invalid();
        }



    }

    protected void printTimes() throws SQLException {
        ResultSet rs = query.executeQuery("SELECT name, cook_time FROM Recipe");
        while (rs.next()) {
            String name = rs.getString("name");
            System.out.println(name);
        }
    }

    private void viewIngredients() {

        System.out.println("Ingredients:\n");
        try {
            ResultSet rs = query.executeQuery("SELECT name FROM Ingredient");
            while (rs.next()) {
                String value = rs.getString("name");
                System.out.println(value);
            }
            System.out.println(" ");
            goBack();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getInput() {
        input = new Scanner(System.in);
        return input.next();
    }


    private void printInstructions() {
        System.out.println("\nMenu:");
        System.out.println("\t1 -> to view ingredients");
        System.out.println("\t2 -> to view recipes");
        System.out.println("\t3 -> to add ingredients");
        System.out.println("\t4 -> to add recipes");
        System.out.println("\tquit -> to quit the program");
    }

    private void goBack() {
        System.out.println("Enter 'quit' to exit the program, \n" +
                "Enter 'menu' to return back to the main menu");
        Scanner selection = new Scanner(System.in);

        switch (selection.nextLine()) {
            case QUIT_COMMAND:
                run = false;
                break;
            case MAIN_MENU_COMMAND:
                runDatabase();
                break;
            default:
                invalid();
        }
    }

    private void goBackOption(String input) {
        if(input.equals(QUIT_COMMAND)) {
            run = false;
        } else if(input.equals(MAIN_MENU_COMMAND)) {
            runDatabase();
        }
    }

}