package wtmpd.cookaid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * @Author: WTMPD Group
 */

public class SingletonStorage extends SQLiteOpenHelper{

    //Singleton
    private static SingletonStorage storage = null;

    //Constants
    private final static String DATABASE_NAME = "CookAid.db";
    private final static String[] TABLES_NAME = {"Recipes"};
    private final static String[] COLUMN_HEADERS = {"Name", "Type", "Cuisine", "Ingredients", "Preptime", "Instructions"};

    //Association
    private List<Recipe> recipes;
    //private List<RecipeCuisine> cuisines;
    //private List<RecipeType> types;
    private List<Ingredient> ingredients;

    //Constructors
    private SingletonStorage(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static SingletonStorage getInstance(Context context){
        storage =  storage == null ? new SingletonStorage(context) : storage;
        return storage;
    }

    //Getters
    public List<Recipe> getRecipes() { return recipes; }
   // public List<RecipeCuisine> getCuisines() { return cuisines; }
    //public List<RecipeType> getTypes() { return types; }
    public List<Ingredient> getIngredients() { return ingredients; }

    //Adders
    public void addRecipe(Recipe newRecipe) { recipes.add(newRecipe); }
    //public void addCuisine(RecipeCuisine newCuisine) { cuisines.add(newCuisine); }
    //public void addType(RecipeType newType) { types.add(newType); }
    public void addIngredient(Ingredient newIngredient) { ingredients.add(newIngredient); }

    //Removers
    public boolean removeRecipe(Recipe recipe) { return recipes.remove(recipe); }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder queryBuilder = new StringBuilder();

        //Build Query
        queryBuilder.append("create table ");
        queryBuilder.append(TABLES_NAME).append("(");
        queryBuilder.append(COLUMN_HEADERS[0]).append(" TEXT PRIMARY KEY");
        for (int i = 1; i < COLUMN_HEADERS.length; i++)
            queryBuilder.append(", ").append(COLUMN_HEADERS[i]).append(" TEXT");
        queryBuilder.append(")");

        db.execSQL(queryBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLES_NAME);
        onCreate(db);
    }
}
