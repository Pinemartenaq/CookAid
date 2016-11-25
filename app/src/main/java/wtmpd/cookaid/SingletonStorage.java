package wtmpd.cookaid;

import java.util.List;

/**
 * Created by William Gardiner on 2016-11-25.
 */

public class SingletonStorage {

    private static SingletonStorage storage = null;

    //Attributes
    private String fileName = "defaul_file_name.txt";

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
    /**
     * Given a file name, this method populates or adds to the lists defined in its associations.
     * @param file
     * @return true if the file exists
     */


    //Public Instance Methods
    public boolean loadIntoStorage(String file){
        fileName = file;
        //ToDo: Implement
        return true; //Temp value
    }

    public void addRecipe(Recipe newRecipe) { recipes.add(newRecipe); }
    public void addType(RecipeType newType){ types.add(newType); }
    public void addIngredient(Ingredient newIngredient){ ingredients.add(newIngredient); }
    //ToDo: Add Instance methods

    //Private Instance Methods

}
