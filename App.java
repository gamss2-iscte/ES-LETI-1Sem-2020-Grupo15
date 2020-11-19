package project.software;

/**
 * Hello world!
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.stream.events.Comment;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
	    
	
	public class App 
	{
			private JFrame frame;
			private JFrame frame2;
			private JLabel erro;
			private JDialog erroDialog;
			private static final String path = "/Users/goncalosantos/Downloads/Defeitos.xlsx";
			private DefaultTableModel model;
			private ArrayList<Regra> regras = new ArrayList<Regra>();
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

							if(linha == 0) { //é a primera linha
						//		columnNames.add(cellValue);
								columnNames2[coluna] = cellValue;
			          
							}else {
								data.add(cellValue);
								//data2[linha][coluna] = cellValue;
			        		   
							}
			        }
				}
			}
			
			public void showExcel() {
				
				
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
			
			public void updateData(ArrayList<Regra> regras) {
				
				
				ArrayList<String> data2 = new ArrayList<String>();
				System.out.println("vou dar update");
				//System.out.println(regras.size());
				
				for(int i =0; i < regras.size(); i++) {
					
					Regra aux = regras.get(i);
					String metrica = aux.getMetrica().toString();
					String operator = aux.getOperator().toString();
					
					System.out.println(metrica);
					System.out.println(operator);
					System.out.println("#########"+aux.getDouble());
				//	System.out.println(aux.getMetrica().toString());
					
					if(metrica.equals("LOC")) {
						
						System.out.println("a métrica é LOC");
						
						if(operator.equals("<")) {
							
							System.out.println("o operador é <");
						
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
							
								System.out.println("Célula do Excel: " + data.get(g+4));
								if(Integer.parseInt(data.get(g+4)) > aux.getDouble()) {
									
									//data.remo
									System.out.println("cheguei");
									//data2.add
									data.set(g+4, "eliminado");
									
								}
							}
						}
					}
					if(aux.getMetrica().equals("CYCLO")) {
						
						
					}
					
					if(aux.getMetrica().equals("ATFD")) {
						
					}
					
					if(aux.getMetrica().equals("LAA")) {
						
						
					}
				}
				//data=data2;
			
			}
			
			
			public void addContent() throws InvalidFormatException, IOException {
				
				frame.setSize(1400, 700);
				frame.setLocation(100, 100);
				frame.setLayout(new BorderLayout());
				frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				erroDialog = new JDialog(frame, "Erro");
				erro = new JLabel();
				erroDialog.add(erro);
				erroDialog.setLocation(600 - 175, 450 - 50);
				erroDialog.setSize(350, 100);

				
				
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
				JButton verExcel = new JButton("Show Excel");
				JButton definirRegras = new JButton("Define Rule");
				JButton verRegras = new JButton("Show Rules");
				JButton exit = new JButton ("Exit");
				hi.add(verExcel);
				hi.add(definirRegras);
				hi.add(verRegras);
				hi.add(exit);
				
			
				verExcel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
					//	model.addRow(columnNames2);
						showExcel();
							
					}
				
				});
	
				
				
				
				definirRegras.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						final Regra regra = new Regra();
						
						final JComboBox cb;
						final JComboBox cb2;
						final String numero;
						
						
						frame2= new JFrame("Set a Rule");
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
						
						//First ComboBox - Métrica
						String [] options= {"Chosse an option", "LOC", "CYCLO", "ATFD", "LAA"};
						cb= new JComboBox(options);
						//regra.setMetrica((String)cb.getSelectedItem());
						
						
						
						//Second ComboBox - Operador
						String [] options2= {"Chosse an option", ">", "<", "="};
						cb2= new JComboBox(options2);
						//regra.setOperator((String)cb2.getSelectedItem());
						
						
						//JTextField - Numero
						JTextField text = new JTextField("10");
						numero = text.getText();
						//regra.setOperator(numero);
						
						
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
						frame2.pack();
						frame2.setVisible(true);
						
						//Carregar no OK do DEFINE RULE
						s.addActionListener(new ActionListener(){
	    					
	    					@Override
	    					public void actionPerformed(ActionEvent e) {
	    						
	    						//check metrica
	    						if(cb.getSelectedItem().equals("Chosse an option") ) {
	    							
	    							erro.setText("Verifique a métrica seleccionada");
	    							erroDialog.setVisible(true);
	    							
	    						}else {
	    							//System.out.println((String)cb.getSelectedItem().toString());
	    							regra.setMetrica((String)cb.getSelectedItem().toString());
	    						}
	    						
	    						//check operator
	    						if(cb2.getSelectedItem().equals("Chosse an option")) {
	    							
	    							erro.setText("Verifique o operador seleccionado");
	    							erroDialog.setVisible(true);
	    							
	    						}else {
	    							regra.setOperator((String)cb2.getSelectedItem().toString());
	    						}
	    						
	    						//check numero
	    						if(isFloat(numero)==false){
	    							erro.setText("Verifique o numero escrito");
	    							erroDialog.setVisible(true);
	    						}else {
	    							System.out.println(numero);
	    							double doub = Integer.parseInt(numero);
	    							regra.setDouble(doub);
	    							
	    						}
	    						
	    						regras.add(regra);
	    						updateData(regras);
	    						showExcel();
	    						
	    						
	    						//FAZER O SCAN DO EXCEL
	    						
	    						
	    						
	    						
	    						
	    					}
	    					
	    				});
						
						s2.addActionListener(new ActionListener(){
	    					
	    					@Override
	    					public void actionPerformed(ActionEvent e) {
	    						//frame2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    						
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
				
				verRegras.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						JFrame frame3 = new JFrame("Edit Rules");
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
						// TODO Auto-generated method stub
						
					}
					
					
					
				});
				
			}
			
			
			private static final Pattern DOUBLE_PATTERN = Pattern.compile(
				    "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
				    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
				    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
				    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

			public static boolean isFloat(String s){
				    
				return DOUBLE_PATTERN.matcher(s).matches();
			}
			

			public static void main(String[] args) throws InvalidFormatException, IOException{
				App g = new App();
				g.open();
			}
		}



