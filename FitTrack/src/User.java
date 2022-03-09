//Object for user: contains username, password, and their daily calorie goal

public class User {

	private String username;
	private String password;
	private int calorieGoal;
	
	public User(String usr, String pwd, int cg) {
		this.username = usr;
		this.password = pwd;
		this.calorieGoal = cg;
	}
	
	public int getCG() {
		return this.calorieGoal;
	}
}
