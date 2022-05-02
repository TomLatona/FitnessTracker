
public class Usermeal {

	public String mealID;
	public String mealname;
	public int calories;
	public String weekday;
	public int weekNumber;
	
	public Usermeal(String mid, String mn, int cal, String wd, int weeknum) {
		this.mealID = mid;
		this.mealname = mn;
		this.calories = cal;
		this.weekday = wd;
		this.weekNumber = weeknum;
	}
	
	public int getCalories() {
		return this.calories;
	}
	
	public String getWeekday() {
		return this.weekday;
	}
	
	public int getWeekNum() {
		return this.weekNumber;
	}
	
	public String getName() {
		return this.mealname;
	}
}
