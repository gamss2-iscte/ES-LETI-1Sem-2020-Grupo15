package project.software;

import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import junit.framework.*;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase{
	
	
	private App app = new App();
   
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */

    public AppTest( String testName ){
    	
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp(){
    			
        assertTrue( true );
    }
    
    public void testRead() throws InvalidFormatException, IOException {
    	
    	app.isFloat("goncalo");
    	app.importarExcel("/Users/goncalosantos/Downloads/Defeitos.xlsx");
    	app.showExcel();
    	
    	Regra rule = new Regra("LOC", ">", 50.0);
    	Regra rule2 = new Regra("LOC", "<", 1000.0);
    	rule2.setDouble(999.0);
    	Regra rule3 = new Regra("LOC", "=", 10.0);
    	Regra rule4 = new Regra("ATFD", ">", 20.0);
    	Regra rule5 = new Regra("ATFD", "<", 1000.0);
    	Regra rule6 = new Regra("ATFD", "=", 10.0);
    	Regra rule7 = new Regra("CYCLO", ">", 20.0);
    	Regra rule8 = new Regra("CYCLO", "<", 1000.0);
    	Regra rule9 = new Regra("CYCLO", "=", 10.0);
    	Regra rule10 = new Regra("LAA", ">", 20.0);
    	Regra rule11 = new Regra("LAA", "<", 1000.0);
    	Regra rule12 = new Regra("LAA", "=", 10.0);
    	
    	Regra rule13 = new Regra("LAA", "=", 0.0);
    	rule13.setMetrica("LOC");
    	rule13.setOperator(">");
    	Regra rule14 = new Regra();
    	
    	app.updateData(app.getRegras());
    	app.addRule(rule);
    	app.addRule(rule2);
    	app.addRule(rule3);
    	app.addRule(rule4);
    	app.addRule(rule5);
    	app.addRule(rule6);
    	app.addRule(rule7);
    	app.addRule(rule8);
    	app.addRule(rule9);
    	app.addRule(rule10);
    	app.addRule(rule11);
    	app.addRule(rule12);
    	app.updateData(app.getRegras());
    
    	app.clearAllRules();
    	app.resetExcel();
    	
    	app.contadores(1); //iPlasma
    	app.resetExcel();
    	
    	app.contadores(2); //PMD
    	app.resetExcel();
    	
    	app.addRule(rule);
    	app.contadores(3);
    	app.clearAllRules();
    	
    	app.addRule(rule4);
    	app.contadores(3);
    	app.clearAllRules();
    	
    	app.updateData2(1);
    	app.clearAllRules();
    	
    	app.updateData2(2);
    	app.clearAllRules();
    	
    	app.main(null);
    	
    }
}
