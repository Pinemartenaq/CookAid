package wtmpd.cookaid;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class RecipeType extends RecipeDescriptor{
    //Constructors
    public RecipeType(String name) { this.name = name; }

    //Filter
    public List<Recipe> filterRecipes(List<Recipe> inputRecipeList){
        List<Recipe> outputRecipeList = new LinkedList<>();
        for (Recipe r : inputRecipeList)
            if(r.getType() == this)
                outputRecipeList.add(r);
        return outputRecipeList;
    }
}
