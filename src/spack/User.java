package spack;

public class User {

	private String name;
	private String email;
	private int type;
	private int id;
	public User() {
		
	}
	
	public User(String _name, String _email, int _type, int _id) {
		setName(_name);
		setEmail(_email);
		setType(_type);
		setID(_id);
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
	public void setID(int _id) {
		this.id = _id;
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
	
	public int getID() {
		return id;
	
	}
}
