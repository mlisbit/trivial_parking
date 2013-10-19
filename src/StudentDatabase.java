
import java.io.IOException;
import java.util.ArrayList;

//the student database, used to manipulate the db file
public class StudentDatabase extends Database {
	
	//Holds the list of students for easy memory access
	private ArrayList<Student> students = new ArrayList<Student>();
	
	//Just need to pass in the filename
	public StudentDatabase(String file) throws IOException {
		super(file);
		this.init();
	}
	
	//open the db file and then load all the students into the students array
	public void init() throws IOException {
		//do not truncate the file on opening
		super.open(false);
		
		String line = null;
	    while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        for(int i = 0; i < parts.length; i++)
	        	parts[i] = parts[i].trim();
	        
	        Student student;
	        //use different kinds of students depending on how complete the account is.
	        if(parts.length == 8)
	        	student = new Student(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6]), parts[7]);	        	
	        else if(parts.length == 7)
	        	student = new Student(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6]));
	        else
	        	student = new Student(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
	        
	        //add the student to the list
	        students.add(student);
	    }
	    //reset the position of the reader
	    this.reader.reset();
	}
	
	//Validate the student number with the given pin
	public Boolean authorizeStudent(int num, int pin) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getPinNumber() == pin) 
				return true;
		return false;
	}
	
	//Validate a students parking status used for assigning a spot
	public Boolean authorizeStatus(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getStatus().equals("ok")) 
				return true;
			else
				return false;
		return false;
	}
	
	//return a student object by their student number
	public Student getStudent(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num)
				return students.get(i);
		return null;
	}
	
	//check to see if a student already has an insurance policy
	public Boolean hasInsurancePolicy(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getParkingSpot() != "") 
				return true;
		return false;
	}
	
	//check to see if a student already has a parking spot
	public Boolean hasParkingSpot(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getInsurancePolicy() != 0) 
				return true;
		return false;
	}
	
	//save a new student to the db file, cannot have duplicate student numbers
	public Boolean saveStudent(int num, int pin, String last, String first) throws IOException {
		//do not truncate the file on opening
		this.open(false);
		
		Student student = new Student(num, pin, last, first, "ok");
		
		for(int i = 0; i < students.size(); i++)
	    	if(student.getStudentNumber() == students.get(i).getStudentNumber())
	    		return false;
		
		students.add(student);
		
		String data = num + "," + pin + "," + last + "," + first + "," + "ok";
		
		//extend the length of the mark used for resetting the db file
		this.reader.mark((int) this.filePath.toFile().length() + data.length() + 1);
		
		this.writer.newLine();
		this.writer.write(data);
		
		this.close();
		
		return true;
	}
	
	//add an insurance company to a students record
	public void saveInsuranceCompany(int num, String company, int policy) throws IOException {
		//truncate the file so we can rebuild it
		this.open(true);
		
		String data = "";
		
		for(int i = 0; i < students.size(); i++) {
	        if(students.get(i).getStudentNumber() == num) {
	        	String[] parts = students.get(i).toString().split(",");
	        	if(parts.length == 5) { //this is the length of an initial record
	        		Student student = students.get(i);
	        		data += student.toString() + "," + company + "," + policy;
	        		students.set(i, new Student(student.getStudentNumber(), student.getPinNumber(), student.getLastName(), student.getFirstName(), student.getStatus(), company, policy));
	        	} else
	        		data += students.get(i).toString();
	        } else
	        	data += students.get(i).toString();
	        
	        if(i < students.size() - 1) 
	        	data += "\n";
	    }
		
		this.writer.write(data);		
		this.close();
	}
	
	/*	save a parking spot to a students record, requires you to pass a ParkingDatabase instance
	 	in order to check and update the status of the parking spot */
	public void saveParkingSpot(int num, String spot, ParkingDatabase pdb) throws IOException {
		//truncate the file so we can rebuild it
		this.open(true);
		
		String data = "";
		
		for(int i = 0; i < students.size(); i++) {
	        if(students.get(i).getStudentNumber() == num) {
	        	String[] parts = students.get(i).toString().split(",");
	        	//make sure the student has an insurance record attached and that the parking spot hasn't been taken
	        	if(parts.length == 7 && pdb.getParkingSpot(spot).getStatus().equals("open")) {
	        		pdb.saveParkingStatus(spot, "reserved"); //update the parking spot
	        		Student student = students.get(i);
	        		data += student.toString() + "," + spot;
	        		students.set(i, new Student(student.getStudentNumber(), student.getPinNumber(), student.getLastName(), student.getFirstName(), student.getStatus(), student.getInsuranceCompany(), student.getInsurancePolicy(), spot));
	        	} else
	         		data += students.get(i).toString();
	        } else
	        	data += students.get(i).toString();
	        
	        if(i < students.size() - 1) 
	        	data += "\n";
	    }
		
		this.writer.write(data);		
		this.close();
	}
}
