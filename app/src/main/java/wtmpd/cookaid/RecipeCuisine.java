package wtmpd.cookaid;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class RecipeCuisine extends RecipeDescriptor{
    //Constructors
    public RecipeCuisine(String name) { this.name = name; }

    //Filter
    public List<Recipe> filterRecipes(List<Recipe> inputRecipeList) {
        List<Recipe> outputRecipeList = new LinkedList<>();
        for (Recipe r : inputRecipeList)
            if (r.getCuisine() == this)
                outputRecipeList.add(r);
        return outputRecipeList;
    }
}
