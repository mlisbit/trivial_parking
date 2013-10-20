
//represents a student and it's current state.
public class Student {
	private int studentNumber;
	private int pinNumber;
	private String lastName;
	private String firstName;
	private String status;
	private String company = "";
	private int policy = 0;
	private String model = "";
	private String spot = "";
	
	public Student(int num, int pin, String last, String first, String status) {
		this.studentNumber = num;
		this.pinNumber = pin;
		this.lastName = last;
		this.firstName = first;
		this.status = status;
	}
	
	public Student(int num, int pin, String last, String first, String status, String insurance, int policy, String model) {
		this.studentNumber = num;
		this.pinNumber = pin;
		this.lastName = last;
		this.firstName = first;
		this.status = status;
		this.company = insurance;
		this.policy = policy;
		this.model = model;
	}
	
	public Student(int num, int pin, String last, String first, String status, String insurance, int policy, String model, String spot) {
		this.studentNumber = num;
		this.pinNumber = pin;
		this.lastName = last;
		this.firstName = first;
		this.status = status;
		this.company = insurance;
		this.policy = policy;
		this.model = model;
		this.spot = spot;
	}
	
	public String getParkingSpot() {
		return this.spot;
	}

	public String getCarModel() {
		return this.model;
	}
	
	public int getInsurancePolicy() {
		return this.policy;
	}
	
	public String getInsuranceCompany() {
		return this.company;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public int getPinNumber() {
		return this.pinNumber;
	}
	
	public int getStudentNumber() {
		return this.studentNumber;
	}	
	
	public Boolean equals(Student student) {
		if(student == this)
			return true;
		if(student.getStudentNumber() == this.studentNumber && student.getPinNumber() == this.pinNumber && student.getLastName().equals(this.lastName) && student.getFirstName().equals(this.firstName) && student.getStatus().equals(this.status) && student.getInsuranceCompany().equals(this.company) && student.getInsurancePolicy() == this.policy && this.getParkingSpot().equals(this.spot))
			return true;
		return false;
	}
	
	public String toString() {
		String student = this.getStudentNumber() + "," + this.getPinNumber() + "," + this.getLastName() + "," + this.getFirstName() + "," + this.getStatus();
		
		if(this.policy > 0)
			student += "," + this.company + "," + this.policy;
		if(this.spot != "")
			student += "," + this.spot;
		
		return student;
	}
}
