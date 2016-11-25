package wtmpd.cookaid;

import java.util.List;

/**
 * Created by William Gardiner on 2016-11-25.
 */

public class Recipe {

    //Attributes
    private String name;
    private int prepTime;
    private String instructions;

    //Associations
    private RecipeCuisine cuisine;
    private RecipeType type;
    private List<SpecificIngredient> ingredients;

    //ToDo: Implement storage

    //Getters
    public String getName() { return name; }
    public int getPrepTime(){ return prepTime; }
    public String getInstructions() { return instructions; }
    public RecipeCuisine getCuisine() { return cuisine; }
    public RecipeType getType() { return type; }
    public List<SpecificIngredient> getIngredients() { return ingredients; }

    //Setters
    public void setName(String newName) { name = newName; }
    public void setPrepTime(int newPrepTime) { prepTime = newPrepTime; }
    public void setInstructions(String newInstructions) { instructions = newInstructions; }
    public void setCuisine(RecipeCuisine newCuisine) { cuisine = newCuisine; }
    public void setType(RecipeType newType) { type = newType; }

    //Public Instance Methods
    /**
     * Adds multiple ingredients to the list of ingredients.
     *
     * @param newIngredients
     */
    public void addIngredients(List<SpecificIngredient> newIngredients) {
        for (SpecificIngredient i : newIngredients);
            //...
    }

    //Instance Methods

    /**
     * Calculates the fitness of Recipe with the desiredIngredients.
     *
     * @param desiredIngredients
     * @return the number of common ingredients
     */
    public int getFitness(List<Ingredient> desiredIngredients) {
        int fitness = 0;
        for (Ingredient i : desiredIngredients)
            fitness += ingredients.contains(i) ? 1 : 0;
        return fitness;
    }
}
