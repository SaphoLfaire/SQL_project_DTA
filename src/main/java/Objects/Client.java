package Objects;

public class Client {
	
	private String lastName;
	private String firstName;
	private Gender gender;
	
	public Client(String lastName, String firstName, Gender gender) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
	}
	
	public Client(String lastName, String firstName) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = Gender.N;
	}
	
	public Client(String lastName) {
		super();
		this.lastName = lastName;
		this.firstName = "";
		this.gender = Gender.N;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public Gender getGender() {
		return gender;
	}

	@Override
	public String toString() {
		return "Client [lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender + "]";
	}

	

	
}