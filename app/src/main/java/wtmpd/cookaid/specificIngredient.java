package wtmpd.cookaid;

/**
 * @Author: WTMPD Group
 */

public class SpecificIngredient {

    //Associations
    Ingredient ingredient;
    String measurement;

    //Constructors
    public SpecificIngredient(Ingredient ingredient, String measurement) {
        this.ingredient = ingredient;
        this.measurement = measurement;
    }

    //Getters
    public Ingredient getIngredient() { return ingredient; }
    public String getMeasurement() { return measurement; }

    //Setters
    public void setMeasurement (String newMeasurement) { measurement = newMeasurement; }

}
