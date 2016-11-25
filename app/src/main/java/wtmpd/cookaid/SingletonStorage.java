package wtmpd.cookaid;

import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class SingletonStorage {

    private static SingletonStorage storage = null;

    //Attributes
    private String fileName = "default_file_name.txt";

    //Association
    private List<Recipe> recipes;
    private List<RecipeCuisine> cuisines;
    private List<RecipeType> types;
    private List<Ingredient> ingredients;

    //Constructors
    public static SingletonStorage getInstance(){
        storage =  storage == null ? new SingletonStorage() : storage;
        return storage;
    }

    //Empty constructor
    public SingletonStorage(){}

    //Getters
    public String getFileName() { return fileName; }
    public List<Recipe> getRecipes() { return recipes; }
    public List<RecipeCuisine> getCuisines() { return cuisines; }
    public List<RecipeType> getTypes() { return types; }
    public List<Ingredient> getIngredients() { return ingredients; }

    //Setters
    public void setFileName(String newName) { fileName = newName; }

    //Adders
    public void addRecipe(Recipe newRecipe) { recipes.add(newRecipe); }
    public void addCuisine(RecipeCuisine newCuisine) { cuisines.add(newCuisine); }
    public void addType(RecipeType newType) { types.add(newType); }
    public void addIngredient(Ingredient newIngredient) { ingredients.add(newIngredient); }

    //Removers
    public boolean removeRecipe(Recipe recipe) { return recipes.remove(recipe); }

    //Private Removers
    private boolean removeCuisine(RecipeCuisine cuisine){ return cuisines.remove(cuisine); }
    private boolean removeType(RecipeType type){ return types.remove(type); }
    private boolean removeIngredient(Ingredient ingredient) { return ingredients.remove(ingredient); }

    //Public Instance Methods
    public boolean loadIntoStorage(){
        //ToDo: Read from file to instance variables
        return true; //Temp value
    }

    public void saveToFile() {
        //Clean up unused ingredients/types/cuisines
        //ToDo Write to file from instance variables
    }
}
