//Object for meals: contains name and number of calories


public class Meal {

	public String name;
	public String calories;
	public String mealID;
	//pubic String type;
	
	public Meal(String n, String c, String id) {
		this.name = n;
		this.calories = c;
		this.mealID = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCalories() {
		return this.calories;
	}
	
	//returns a string with the full insert statement for the SQL database
	public String addMealtoDB() {
		String addMeal = "insert into *db* (column1, column2) VALUES (" + this.name +", " + this.calories +");";
		return addMeal;
	}
}
