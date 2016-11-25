package wtmpd.cookaid;

import java.util.LinkedList;
import java.util.List;

/**
 * Recipe class
 *
 * Stores information about the recipe.
 *
 * @Author: WTMPD Group
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
    private SingletonStorage storage;

    //Constructor
    public Recipe(String name, int prepTime, RecipeCuisine cuisine, RecipeType type, List<SpecificIngredient> ingredients){
        this.name = name;
        this.prepTime = prepTime;
        this.cuisine = cuisine;
        this.type = type;
        this.ingredients = ingredients;
        storage = SingletonStorage.getInstance();
    }

    //Getters
    public String getName() { return name; }
    public int getPrepTime(){ return prepTime; }
    public String getInstructions() { return instructions; }
    public RecipeCuisine getCuisine() { return cuisine; }
    public RecipeType getType() { return type; }
    public List<SpecificIngredient> getSpecificIngredients() { return ingredients; }
    public List<Ingredient> getIngredients() {
        List ingredientList = new LinkedList<Ingredient>();
        for (SpecificIngredient si : ingredients) {
            if(!ingredientList.contains(si.getIngredient()))
                ingredientList.add(si.getIngredient());
        }
        return ingredientList;
    }

    //Setters
    public void setName(String newName) { name = newName; }
    public void setPrepTime(int newPrepTime) { prepTime = newPrepTime; }
    public void setInstructions(String newInstructions) { instructions = newInstructions; }
    public void setCuisine(RecipeCuisine newCuisine) { cuisine = newCuisine; }
    public void setType(RecipeType newType) { type = newType; }

    /**
     * Adds multiple ingredients to the list of ingredients.
     *
     * @param newIngredients
     */
    public void addIngredients(List<SpecificIngredient> newIngredients) {
        for (SpecificIngredient i : newIngredients)
            ingredients.add(i);
    }

    //Public Instance Methods

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
