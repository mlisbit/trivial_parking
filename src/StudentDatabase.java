package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;


public class StudentDatabase extends Database {
	
	private ArrayList<Student> students = new ArrayList<Student>();
	
	public StudentDatabase(String file) throws IOException {
		super(file);
		
		String line = null;
	    while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        for(int i = 0; i < parts.length; i++)
	        	parts[i] = parts[i].trim();
	        
	        Student student;
	        if(parts.length == 8)
	        	student = new Student(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6]), parts[7]);	        	
	        else if(parts.length == 7)
	        	student = new Student(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], parts[4], parts[5], Integer.parseInt(parts[6]));
	        else
	        	student = new Student(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
	        
	        students.add(student);
	    }
	}
	
	public Boolean authorizeStudent(int num, int pin) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getPinNumber() == pin) 
				return true;
		return false;
	}
	
	public Student getStudent(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num)
				return students.get(i);
		return null;
	}
	
	public Boolean hasInsurancePolicy(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getParkingSpot() != "") 
				return true;
		return false;
	}
	
	public Boolean hasParkingSpot(int num) {
		for(int i = 0; i < students.size(); i++)
			if(students.get(i).getStudentNumber() == num && students.get(i).getInsurancePolicy() != 0) 
				return true;
		return false;
	}
	
	public Boolean saveStudent(int num, int pin, String last, String first, String status) throws IOException {
		Student student = new Student(num, pin, last, first, status);
		
		for(int i = 0; i < students.size(); i++)
	    	if(student.equals(students.get(i)))
	    		return false;
		
		students.add(student);
		this.writer.newLine();
		this.writer.write(num + ',' + pin + ',' + last + ',' + first + ',' + status);
		return true;
	}
	
	public Boolean saveInsuranceCompany(int num, String company, int policy) throws IOException {
		String line = null;
		String data = "";
		
		this.reader.reset();
		while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        if(Integer.parseInt(parts[0]) == num) {
	        	if(parts.length == 7)
	        		return false;
	        	data += line + "," + company + "," + policy;
	        } else
	        	data += line;
	    }
		this.writer.write(data);
		return true;
	}
	
	public Boolean saveParkingSpot(int num, String spot) throws IOException {
		String line = null;
		String data = "";
		
		this.reader.reset();
		while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        if(Integer.parseInt(parts[0]) == num) {
	        	if(parts.length != 7)
	        		return false;
	        	data += line + "," + spot;
	        } 
	        else
	        	data += line;
	    }
		writer.write(data);
		return true;
	}
}
