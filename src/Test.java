package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
	public static void main(String args[]) throws IOException {
		StudentDatabase sdb = new StudentDatabase("students.txt");
		InsuranceDatabase idb = new InsuranceDatabase("companies.txt");
		ParkingDatabase pdb = new ParkingDatabase("parking.txt");
		
		Student testS1 = sdb.getStudent(123456789);
		
		System.out.println(testS1);
		
		Parking testP1 = pdb.getParkingSpot("1a09");
		
		System.out.println(testP1);
		
		ArrayList<Insurance> companies = idb.getCompanies();
		
		for(int i = 0; i < companies.size(); i++) {
			System.out.println(companies.get(i));
		}
	}
}
