
public class Usermeal {

	public String mealID;
	public int calories;
	public String weekday;
	public int weekNumber;
	
	public Usermeal(String mid, int cal, String wd, int weeknum) {
		this.mealID = mid;
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
}
