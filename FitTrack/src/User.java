//Object for user: contains username, password, and their daily calorie goal

public class User {

	private String username;
	private String password;
	private String calorieGoal;
	private String userID;
	
	public User(String usr, String pwd, String cg, String id) {
		this.username = usr;
		this.password = pwd;
		this.calorieGoal = cg;
		this.userID = id;
	}
	
	public String getCG() {
		return this.calorieGoal;
	}
	
	public String getPass() {
		return this.password;
	}
	
	//returns a string with the full insert statement for the SQL database
	public String addUserToDB() {
		String addUser = "insert into *db* (column1, column2, column3) VALUES (" + this.username +", " + this.password +", "+this.calorieGoal+");";
		return addUser;
	}
}
