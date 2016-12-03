package wtmpd.cookaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class SingletonStorage extends SQLiteOpenHelper{

    //Singleton
    private static SingletonStorage storage = null;

    //Id creator
    private static int id = 0;

    //Constants
    private final static String DATABASE_NAME = "CookAid.db";
    private final static String TABLE_NAME = "Recipes";
    private final static String[] COLUMN_HEADERS = {"ID", "Name", "Type", "Cuisine", "Ingredients", "Preptime", "Instructions"};

    //Public variables used to pass information from one activity to another
    public LinkedList<String> recipeNames;
    public LinkedList<String> recipeFitnesses;
    public Recipe storedRecipe;

        //Query results
    public String queryByName = null;
    public String queryByIngredients = null;
    public RecipeType queryType = null;
    public RecipeCuisine queryCuisine = null;

    //Association
    private List<Recipe> recipes;
    private List<RecipeCuisine> cuisines;
    private List<RecipeType> types;
    private List<Ingredient> ingredients;

    //Constructors
    private SingletonStorage(Context context) {
        super(context, DATABASE_NAME, null, 1);

        //Initialize Lists
        recipes = new ArrayList<>();
        cuisines = new ArrayList<>();
        types = new ArrayList<>();
        ingredients = new ArrayList<>();

        //Create objects
        createObjectsFromDatabase();
    }

    public static SingletonStorage getInstance(Context context){
        storage =  storage == null ? new SingletonStorage(context) : storage;
        return storage;
    }

    //Getters
    public List<Recipe> getRecipes(){ return recipes; }
    public List<RecipeType> getTypes(){ return types; }
    public List<RecipeCuisine> getCuisines(){ return cuisines; }

    //Adders
    public void add(RecipeType newType){ types.add(newType); }
    public void add(RecipeCuisine newCuisine){ cuisines.add(newCuisine); }
    public void add(Ingredient newIngredient){ ingredients.add(newIngredient); }

    //Public Methods
    public boolean addRecipe(Recipe recipe){
        int id = addRecipeToDatabase(recipe);
        if (id == -1) {
            return false;
        }
        recipe.setID(id);
        return true;
    }

    public boolean updateRecipe(Recipe recipe){
        return updateRecipeInDatabase(recipe);
    }

    public boolean deleteRecipe(Recipe recipe){
        boolean result =  removeRecipeFromDatabase(recipe);
        if(result)
            result = recipes.remove(recipe);
        return result;
    }

    //Database functions
    /**
     * Adds the new recipe to the database, then returns the id of the recipe
     *
     * @param recipe
     * @return recipe id
     */
    private int addRecipeToDatabase(Recipe recipe) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues entry = new ContentValues();

        StringBuilder ingredientsString = new StringBuilder();
        for(SpecificIngredient si : recipe.getSpecificIngredients())
            ingredientsString.append(si.getIngredient()).append(", ").append(si.getMeasurement()).append(", ");

        entry.put(COLUMN_HEADERS[1], recipe.getName());
        entry.put(COLUMN_HEADERS[2], recipe.getType().getName());
        entry.put(COLUMN_HEADERS[3], recipe.getCuisine().getName());
        entry.put(COLUMN_HEADERS[4], ingredientsString.toString());
        entry.put(COLUMN_HEADERS[5], Integer.toString(recipe.getPrepTime()));
        entry.put(COLUMN_HEADERS[6], recipe.getInstructions());

        db.insert(TABLE_NAME, null, entry);

        Cursor cursor = db.rawQuery("SELECT " + COLUMN_HEADERS[0] + " FROM " + TABLE_NAME, null);
        cursor.moveToLast();

        return Integer.parseInt(cursor.getString(0));
    }

    /**
     * Updates a recipe in the database
     *
     * @param updatedRecipe
     * @return true if update was sucessful
     */
    private boolean updateRecipeInDatabase(Recipe updatedRecipe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues entry = new ContentValues();

        StringBuilder ingredientsString = new StringBuilder();
        for(SpecificIngredient si : updatedRecipe.getSpecificIngredients())
            ingredientsString.append(si.getIngredient()).append(", ").append(si.getMeasurement()).append(", ");
        ingredientsString.delete(ingredientsString.lastIndexOf(", "), ingredientsString.lastIndexOf(", ") + 1);

        entry.put(COLUMN_HEADERS[1], updatedRecipe.getName());
        entry.put(COLUMN_HEADERS[2], updatedRecipe.getType().getName());
        entry.put(COLUMN_HEADERS[3], updatedRecipe.getCuisine().getName());
        entry.put(COLUMN_HEADERS[4], ingredientsString.toString());
        entry.put(COLUMN_HEADERS[5], Integer.toString(updatedRecipe.getPrepTime()));
        entry.put(COLUMN_HEADERS[6], updatedRecipe.getInstructions());

        String[] value = {updatedRecipe.getID()};

        return db.update(TABLE_NAME, entry, COLUMN_HEADERS[0] + " = ?", value) != 0;

    }

    /**
     * Deletes a recipe from the database
     *
     * @param recipe
     * @return true if deletion was successful
     */
    private boolean removeRecipeFromDatabase(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] value = {recipe.getID()};

        return db.delete(TABLE_NAME, "ID = ?", value) != 0;
    }

    /**
     *
     * @return
     */
    private boolean createObjectsFromDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cursor.getCount() > 0)
            cursor.moveToFirst();
            for (;!cursor.isAfterLast(); cursor.moveToNext()) {
                Recipe newRecipe = new Recipe(
                        cursor.getString(1), //name
                        Integer.parseInt(cursor.getString(5)), //time
                        getCuisine(cursor.getString(3)), //cuisine
                        getType(cursor.getString(2)), //type
                        getSpecificIngredientsFromString(cursor.getString(4)), //ingredients
                        cursor.getString(6) //instructions
                );
                newRecipe.setID(cursor.getInt(0)); //ID
                recipes.add(newRecipe);
            }
        return true;

    }

    //Other Private Methods
    private List<SpecificIngredient> getSpecificIngredientsFromString(String ingredientString){

        List<SpecificIngredient> ingredientList = new ArrayList<SpecificIngredient>();
        String[] ingredientArray = ingredientString.split(",");

        for(int i = 0; i < ingredientArray.length - 1; i+=2){
            ingredientList.add(new SpecificIngredient(getIngredient(ingredientArray[i]), ingredientArray[i+1]));
        }

        return ingredientList;
    }

    /**
     * Gets or creates an ingredient with the given name
     *
     * @param ingredientName
     * @return ingredient with correct name
     */
    public Ingredient getIngredient(String ingredientName){

        Ingredient ingredient = null;

        for (Ingredient i : ingredients)
            if(i.getName().equals(ingredientName)) ingredient = i;

        if(ingredient == null){
            ingredient = new Ingredient(ingredientName);
            ingredients.add(ingredient);
        }

        return ingredient;
    }

    public RecipeType getType(String typeName){

        RecipeType type = null;

        for (RecipeType t : types)
            if(t.getName().equals(typeName)) type = t;

        if(type == null){
            type = new RecipeType(typeName);
            types.add(type);
        }

        return type;

    }

    public RecipeCuisine getCuisine(String cuisineName){

        RecipeCuisine cuisine = null;

        for (RecipeCuisine c : cuisines)
            if(c.getName().equals(cuisineName)) cuisine = c;

        if(cuisine == null){
            cuisine = new RecipeCuisine(cuisineName);
            cuisines.add(cuisine);
        }

        return cuisine;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder queryBuilder = new StringBuilder();

        //Build Query
        queryBuilder.append("create table ");
        queryBuilder.append(TABLE_NAME).append("(");
        queryBuilder.append(COLUMN_HEADERS[0]).append(" INTEGER PRIMARY KEY AUTOINCREMENT");
        for (int i = 1; i < COLUMN_HEADERS.length; i++)
            queryBuilder.append(", ").append(COLUMN_HEADERS[i]).append(" TEXT");
        queryBuilder.append(")");

        db.execSQL(queryBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
