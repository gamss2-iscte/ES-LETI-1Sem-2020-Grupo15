package EPA ;

/**
 * Hello world!
 *
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.stream.events.Comment;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
public class App {
    
    
  
    	    
    	
    			private JFrame frame;
    			private JFrame frame2;
    			private JFrame frame3;
    			private static final String path = "/Users/catri/Downloads/Defeitos.xlsx";
    			private DefaultTableModel model;
    			
    			private String[] columnNames2;
    			private ArrayList<String> data = new ArrayList<String>();
    			
    			public App() throws InvalidFormatException, IOException {
    				frame = new JFrame ("Excel Reader");
    				addContent();
    			}
    			
    			public void open() {
    				frame.setVisible(true);
    			}
    			
     			public int numberOfColumns(Sheet sheet) {
    				
    				int numberOfCells = 0;
    				Iterator rowIterator = sheet.rowIterator();
    				if (rowIterator.hasNext()){
    					
    					Row headerRow = (Row) rowIterator.next();
    					//get the number of cells in the header row
    					numberOfCells = headerRow.getPhysicalNumberOfCells();
    				}
    				return numberOfCells;
    			}
    			
     			
     			
    			public void importarExcel(String path) throws InvalidFormatException, IOException {
    				
    				Workbook workbook = WorkbookFactory.create(new File(path));
    				Sheet sheet = workbook.getSheetAt(0);
    				DataFormatter dataFormatter = new DataFormatter();
    				
                    
                 //   ArrayList<String> columnNames = new ArrayList<String>();
                    
                   columnNames2 = new String[numberOfColumns(sheet)];
                //    String[][] data2 = new String[numberOfCells][sheet.getLastRowNum()];
    										
                    int linha = -1;
                    int coluna = -1;
                    
    				for (Row row: sheet) {
    					linha++;
    					for(Cell cell: row) {
    						coluna++;
    			        	 
    						String cellValue = dataFormatter.formatCellValue(cell);

    							if(linha == 0) { //Ã© a primera linha
    						//		columnNames.add(cellValue);
    								columnNames2[coluna] = cellValue;
    			          
    							}else {
    								data.add(cellValue);
    								//data2[linha][coluna] = cellValue;
    			        		   
    							}
    			        }
    				}
    			}
    			
    			
    			
    			
    			public void addContent() throws InvalidFormatException, IOException {
    				
    				frame.setSize(1400, 700);
    				frame.setLocation(100, 100);
    				frame.setLayout(new BorderLayout());
    				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    				
    				
    				//Tabela Excel
    				
    				importarExcel(this.path);
    				JScrollPane scroll;
    				model = new DefaultTableModel();
    				model.setColumnIdentifiers(columnNames2);
    				JTable excel = new JTable();
    				excel.setModel(model);
    				scroll = new JScrollPane(excel);
    				
    				
    				frame.add(scroll);
    				scroll.setBounds(20,20,300,300);
    				
    				
    				//Painel Sul
    				JPanel hi = new JPanel();
    				frame.add(hi, BorderLayout.SOUTH);
    				hi.setLayout(new FlowLayout());
    				JButton verExcel = new JButton("Open Excel");
    				JButton definirRegras = new JButton("Define Rules");
    				JButton editarRegras= new JButton("Edit Rules");
    				JButton exit = new JButton ("Exit");
    				hi.add(verExcel);
    				hi.add(definirRegras);
    				hi.add(editarRegras);
    				hi.add(exit);
    				
    			
    				verExcel.addActionListener(new ActionListener() {

    					
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						
    					//	model.addRow(columnNames2);
    						
    						Object[] linha = new Object[columnNames2.length];
    						int auxiliar=-1;
    						
    						for(int w = 0; w < data.size(); w+=columnNames2.length) {
    							auxiliar++;
    							for (int i = 0; i < columnNames2.length; i++) {
    							
    								linha[i]=data.get(i+(auxiliar*columnNames2.length));
    							}
    							model.addRow(linha);
    						}	
    					}
    					
    					
    					
    					
    					
    				});
    				
    				
    				
    				
               editarRegras.addActionListener(new ActionListener() {

    					
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						
    						frame3=new JFrame("Edit Rules");
    						frame3.setSize(800, 500);
    						frame3.setLocation(100, 100);
    						frame3.setLayout(new FlowLayout());
    						frame3.setVisible(true);
    						
    						
    						
    						JPanel panel= new JPanel();
    						
    	    				frame3.add(panel, BorderLayout.NORTH);
    	    				
    	    				frame3.setLayout(new FlowLayout());
    	    				
    						JLabel name= new JLabel("Name");
    						JLabel metric=new JLabel("Metric");
    						JLabel operator= new JLabel("Operator");
    						JLabel number= new JLabel("Number");
    						
    						
    						
    						
    					    panel.add(name);
    					    panel.add(metric);
    					    panel.add(operator);
    					    panel.add(number);
    					    
    					    
    					   
    						
    						
    	
    					}
    					
    					
    					
    					
    					
				});
               
               
               
				
    				definirRegras.addActionListener(new ActionListener() {

    					@Override
    					public void actionPerformed(ActionEvent e) {
    						
    						frame2= new JFrame("Set the Rules");
    						frame2.setSize(800, 500);
    						frame2.setLocation(100, 100);
    						frame2.setLayout(new BorderLayout());
    						frame2.setVisible(true);
    						// TODO Auto-generated method stub
    						
    						

    						
    						JPanel buttonPane= new JPanel();
    						JPanel fieldsPanel= new JPanel();
    						JLabel metrics=new JLabel("Metrics");
    						JLabel operator= new JLabel("Operator");
    						JLabel number= new JLabel("Number");
    						
    						//First ComboBox
    						String [] options= {"", "LOC", "CYCLO", "ATFD", "LAA"};
    						JComboBox cb= new JComboBox(options);
    						
    						//Second ComboBox
    						String [] options2= {"", ">", "<", "="};
    						JComboBox cb2= new JComboBox(options2);
    						
    						//JTextField
    						
    						JTextField text = new JTextField("");
    						
    						//Botão Normal
    						
    						JButton s= new JButton("OK");
    						JButton s2= new JButton("Cancel");
    						
    						fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
    						buttonPane.setLayout(new FlowLayout());
    						
    						fieldsPanel.add(metrics);
    						fieldsPanel.add(cb);
    						fieldsPanel.add(operator);
    						fieldsPanel.add(cb2);
    						fieldsPanel.add(number);
    						fieldsPanel.add(text);
    						
    						buttonPane.add(s);
    						buttonPane.add(s2);
    						
    						frame2.add(fieldsPanel, BorderLayout.PAGE_START);
    						frame2.add(buttonPane, BorderLayout.PAGE_END);
    						frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    						frame2.pack();
    						frame2.setVisible(true);
    						
    						s.addActionListener(new ActionListener(){
    	    					
    	    					@Override
    	    					public void actionPerformed(ActionEvent e) {
    	    						//FAZER O SCAN DO EXCEL
    	    						
    	    					}
    	    					
    	    				});
    						
    						s2.addActionListener(new ActionListener(){
    	    					
    	    					@Override
    	    					public void actionPerformed(ActionEvent e) {
    	    						frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    	    						
    	    					}
    	    					
    	    				});
    						

    						
    					}
    					
    					
    					
    					
    				});
    				
    				
    				exit.addActionListener(new ActionListener(){
    					
    					@Override
    					public void actionPerformed(ActionEvent e) {
    						frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    						
    					}
    					
    				});
    				
    			}
    			
    			

    			public static void main(String[] args) throws InvalidFormatException, IOException{
    				App g = new App();
    				g.open();
    			}
    		}





