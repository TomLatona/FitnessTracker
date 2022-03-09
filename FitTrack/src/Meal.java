//Object for meals: contains name and number of calories


public class Meal {

	public String name;
	public int calories;
	
	public Meal(String n, int c) {
		this.name = n;
		this.calories = c;
	}
	
	public String getMealName() {
		return this.name;
	}
	
	public int getMealCalories() {
		return this.calories;
	}
}
