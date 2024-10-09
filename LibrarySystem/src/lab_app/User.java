package lab_app;

public class User {
	public User(String username, String pwd, Auth auth) {
		this.username = username;
		password = pwd;
		authorization = auth;
	}
	public String username;
	public String password;
	public Auth authorization;

	@Override
	public boolean equals(Object ob) {
		if((ob == null) || !(ob instanceof User)) {
			return false;
		}
		User u = (User)ob;
		return username.equals(u.username) && password.equals(u.password);
	}
}
