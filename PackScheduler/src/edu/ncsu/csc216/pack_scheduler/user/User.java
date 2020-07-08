package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Class representing a User: basic info and credentials, whilst
 * providing functionality such as creating a User, setting info and
 * credentials, and comparing two Users.
 * 
 * @author Ethan Taylor
 */
public class User {

	/** The User's first name */
	protected String firstName;
	/** The User's last name */
	protected String lastName;
	/** The User's id number */
	protected String id;
	/** The User's email */
	protected String email;
	/** The User's hashed password */
	protected String hashedPassword;

	/**
	 * Constructs the User object
	 * 
	 * @param firstName the User's first name
	 * @param lastName the User's last name
	 * @param id the User's id
	 * @param email the User's email
	 * @param hashPW the User's hashedPW
	 */
	public User(String firstName, String lastName, String id, String email, String hashPW) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
	}
	
	/**
	 * Gets the User's first name
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the User's first name
	 * 
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		if (firstName == null) {
			throw new IllegalArgumentException("Invalid first name");
		}
		if ("".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Sets the User's last name
	 * 
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		if (lastName == null) {
			throw new IllegalArgumentException("Invalid last name");
		}
		if ("".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Gets the User's last name
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the User's email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the User's email
	 * 
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("Invalid email");
		}
		if ("".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		boolean hasAt = false;
		int atIndex = 0;
		boolean hasPeriod = false;

		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				hasAt = true;
				atIndex = i;
			}
		}

		for (int i = atIndex; i < email.length(); i++) {
			if (email.charAt(i) == '.') {
				hasPeriod = true;
			}
		}
		if (!(hasAt && hasPeriod)) {
			throw new IllegalArgumentException("Invalid email");
		}

		this.email = email;
	}
	
	/**
	 * Gets the User's id
	 * 
	 * @return the User's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the User's id
	 * 
	 * @param id the id to set
	 */
	private void setId(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id");
		}
		if (id.equals("")) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}
	
	/**
	 * Gets the User's password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return hashedPassword;
	}

	/**
	 * Sets the User's password
	 * 
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		if (password == null) {
			throw new IllegalArgumentException("Invalid password");
		}
		if ("".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.hashedPassword = password;
	}

	/**
	 * Generates a hashCode that represents the fields in User
	 * 
	 * @return the hashCode for course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((hashedPassword == null) ? 0 : hashedPassword.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/**
	 * Compares a given course object for equality to all fields in User
	 * 
	 * @param obj the object to compare
	 * @return true if the objects are the same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (hashedPassword == null) {
			if (other.hashedPassword != null)
				return false;
		} else if (!hashedPassword.equals(other.hashedPassword))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}