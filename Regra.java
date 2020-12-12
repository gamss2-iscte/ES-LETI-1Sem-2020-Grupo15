package project.software;

public class Regra {

	private String metrica;
	private String operator;
	private double x;
	
	
	/**
	 * This constructor constructs an "Regra"
	 */
	public Regra() {}
	
	
	/**
	 * This constructor constructs an "Regra"
	 * @param metrica
	 * @param operator
	 * @param x
	 */
	public Regra(String metrica, String operator, Double x) {
		this.metrica=metrica;
		this.operator=operator;
		this.x=x;
		
	}
	

	/**
	 * This method change the attribute "Metrica"
	 * @param a: String to be the "Metrica"
	 */
	public void setMetrica(String a) {
			
			this.metrica=a;
	}
	
	
	/**
	 * This method change the attribute "Operator"
	 * @param a: String to be the "Operator"
	 */
	public void setOperator(String a) {
		
			this.operator=a;	
	}
	
	/**
	 * This method change the attribute "Double"
	 * @param x: Double to be the "Double"
	 */
	public void setDouble(Double x) {
		
		this.x=x;
	}

	/**
	 * This method returns the attribute "Double"
	 * @return attribute "Double"
	 */
	public double getDouble() {
		return x;
	}

	/**
	 * This method returns the attribute "Metrica"
	 * @return attribute "Metrica"
	 */
	public String getMetrica() {
		return metrica;
	}

	/**
	 * This method returns the attribute "Operator"
	 * @return attribute "Operator"
	 */
	public String getOperator() {
		return operator;
	}
	
}
