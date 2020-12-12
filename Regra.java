package project.software;

public class Regra {

	private String metrica;
	private String operator;
	private double x;
	
	
	public Regra() {}
	
	public Regra(String metrica, String operator, Double x) {
		this.metrica=metrica;
		this.operator=operator;
		this.x=x;
		
	}
	public void setMetrica(String a) {
			
			this.metrica=a;
	}
	
	public void setOperator(String a) {
		
		
			this.operator=a;	
	}
	
	public void setDouble(Double x) {
		
		this.x=x;
	}

	public double getDouble() {
		return x;
	}

	public String getMetrica() {
		return metrica;
	}

	public String getOperator() {
		return operator;
	}
	
}
