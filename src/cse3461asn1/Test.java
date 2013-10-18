package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;

public class Test {
	public static void main(String args[]) throws IOException {
		StudentDatabase sdb = new StudentDatabase("students.txt");
		InsuranceDatabase idb = new InsuranceDatabase("companies.txt");
		ParkingDatabase pdb = new ParkingDatabase("parking.txt");
		
		ArrayList<Insurance> companies = idb.getCompanies();
		
		sdb.saveStudent(987612345, 2430, "Anderson", "Devon");
		sdb.saveInsuranceCompany(987612345, companies.get(0).getName(), 99882211);
		sdb.saveParkingSpot(987612345, "1a03", pdb);
	}
}
