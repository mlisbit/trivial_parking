package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;

public class InsuranceDatabase extends Database {
	
	private ArrayList<Insurance> companies = new ArrayList<Insurance>();
	
	public InsuranceDatabase(String file) throws IOException {
		super(file);
		
		String line = null;
	    while ((line = this.reader.readLine()) != null) {
	        Insurance company = new Insurance(line.trim());
	        companies.add(company);
	    }
	}
	
	public ArrayList<Insurance> getCompanies() {
		ArrayList<Insurance> copy = new ArrayList<Insurance>();
		copy.addAll(companies);
		return copy;
	}
}
