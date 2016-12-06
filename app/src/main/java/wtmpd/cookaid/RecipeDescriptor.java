package wtmpd.cookaid;

import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class RecipeDescriptor {
    //Attributes
    String name;

    //Associations
    private List<Recipe> recipes;

    //Getters
    public String getName() { return name; }
    public List<Recipe> getRecipes() { return recipes; }
    public boolean hasRecipes() { return recipes.size() != 0; }

    //Setters
    public boolean removeRecipe(Recipe oldRecipe) { return recipes.remove(oldRecipe); }
}
