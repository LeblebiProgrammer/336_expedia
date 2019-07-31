package spack;

public class User {

	private String name;
	private String email;
	private int type;
	
	public User() {
		
	}
	
	public User(String _name, String _email, int _type) {
		setName(_name);
		setEmail(_email);
		setType(_type);
	}
	
	public void setName(String _name) {
		this.name = _name;
	}
	
	public void setEmail(String _email) {
		this.email = _email;
	}
	
	public void setType(int _type) {
		this.type = _type;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getType() {
		return type;
	}
}
