package application;

//Model class for searching stuff
public class AccountSearchModel {
	String username, password, email, website;
	
	//Constructor 
	public AccountSearchModel(String username, String password, String email, String website) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.website = website;
	}

	//Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
	
}
