package wtmpd.cookaid;

import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class Ingredient {

    //Attributes
    String name;

    //Associations
    List<Recipe> recipes;

    //Constructors
    public Ingredient(String name) { this.name = name; }

    //Getters
    public List<Recipe> getRecipes() { return recipes; }
    public boolean hasRecipes() { return recipes.size() != 0; }

    //Setters
    public void addRecipe(Recipe newRecipe) { recipes.add(newRecipe); }
    public boolean removeRecipe(Recipe oldRecipe) { return recipes.remove(oldRecipe); }
}
