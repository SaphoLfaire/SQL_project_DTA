package Objects;

public class Client {

	private String lastName;
	private String firstName;
	private Gender gender;
	private int idFav;
	private int id;

	public Client(String lastName, String firstName, Gender gender, int idFav) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.gender = gender;
		this.idFav = idFav;
	}

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

	public int getIdFav() {
		return idFav;
	}

	public int getId() {
		return id;
	}

	public void setIdFav(int id) {
		this.idFav = id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Client [lastName=" + lastName + ", firstName=" + firstName + ", gender=" + gender + "]";
	}

}
