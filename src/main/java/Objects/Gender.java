package Objects;

public enum Gender {
	F("F"), M("M"), N("0");

	private String gender = "";

	Gender(String gender) {

		this.gender = gender;

	}

	public String toString() {

		return gender;

	}

}
