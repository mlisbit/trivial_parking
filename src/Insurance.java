package cse3461asn1;

//represents and insurance company
public class Insurance {
	
	private String name;
	
	public Insurance(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Boolean equals(Insurance company) {
		if(company == this)
			return true;
		if(company.getName().equals(this.name))
			return true;
		return false;
	}
	
	public String toString() {
		return this.name;
	}
}
