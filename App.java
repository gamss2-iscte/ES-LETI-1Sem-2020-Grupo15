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
import java.util.Vector;
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
			private JFrame frame_principal;
			private JFrame frame_setRule;
			private JFrame frame_codeSmells;
			private JFrame frame_evaluteQuality;
			private JFrame frame_resultados;
			private JFrame frame_deleteRule;
			private JLabel erro;
			private JDialog erroDialog;
			private static final String path = "/Users/goncalosantos/Downloads/Defeitos.xlsx";
			private DefaultTableModel model;
			private ArrayList<Regra> regras = new ArrayList<Regra>();
			private String[] columnNames2;
			private ArrayList<String> data = new ArrayList<String>();
			private JTextField text = new JTextField("");
			
			private DefaultListModel<String> lista_modelo;
			
			
			public App() throws InvalidFormatException, IOException {
				frame_principal = new JFrame ("Excel Reader");
				addContent();
			}
			
			
			public void open() {
				frame_principal.setVisible(true);
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

			
 			public void clearTable() {
				
				
				int rowCount = model.getRowCount();
				//Remove rows one by one from the end of the table
				for (int i = rowCount - 1; i >= 0; i--) {
				    model.removeRow(i);
				}
				
			}
			
			
 			public void removeFromData(int a, int referencia) {
				
				if (a == 4) { //LOC
					
					data.remove(referencia - 4);
					data.remove(referencia - 3);
					data.remove(referencia - 2);
					data.remove(referencia - 1);
					data.remove(referencia);
					data.remove(referencia + 1);
					data.remove(referencia + 2);
					data.remove(referencia + 3);
					data.remove(referencia + 5);
					data.remove(referencia + 6);
					data.remove(referencia + 7);
				}
			}
					
			
 			public void addToData(ArrayList<String> objetivo, ArrayList<String> original, int g) {
				
				objetivo.add(original.get(g));
				objetivo.add(original.get(g + 1));
				objetivo.add(original.get(g + 2));
				objetivo.add(original.get(g + 3));
				objetivo.add(original.get(g + 4));
				objetivo.add(original.get(g + 5));
				objetivo.add(original.get(g + 6));
				objetivo.add(original.get(g + 7));
				objetivo.add(original.get(g + 8));
				objetivo.add(original.get(g + 9));
				objetivo.add(original.get(g + 10));
				objetivo.add(original.get(g + 11));
				
			}
				
			
 			public void updateData(ArrayList<Regra> regras) {
				
				clearTable();
				
				ArrayList<String> data2 = new ArrayList<String>();
				System.out.println("Neste momento existem " + regras.size() + " regras");
				
				
				if(regras.isEmpty()) {
				//	System.out.println("TESTE TESTE TESTE");
					try {
						data.clear();
						importarExcel(path);
						data2=data;
					} catch (InvalidFormatException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				for(int i = 0; i < regras.size(); i++) {
					
					Regra aux = regras.get(i);
					String metrica = aux.getMetrica().toString();
					String operator = aux.getOperator().toString();
					data2.clear();
					//System.out.println("#########"+aux.getDouble());
					
					if(metrica.equals("LOC")) {  //metric is LOC
						
							if(operator.equals("<")) { //operator is <
							
								for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
									//System.out.println("Célula do Excel: " + data.get(g+4));
									
									if(Integer.parseInt(data.get(g+4)) < aux.getDouble()) {
										
										//System.out.println("cheguei");
										addToData(data2, data, g);		
										//data2.add
										//removeFromData(4, g+4);
										//data.set(g+4, "eliminado");
										
									}
								}
							}
							
							if (operator.equals(">")) { //operator is >
								
								for(int g = 0; g < data.size(); g+=columnNames2.length) {
									
									if(Integer.parseInt(data.get(g+4)) > aux.getDouble()) {
										
										addToData(data2, data, g);		
										
									}
								}
							}
							if (operator.equals("=")) { //operator is =
								
								for(int g = 0; g < data.size(); g+=columnNames2.length) {
									
									if(Integer.parseInt(data.get(g+4)) == aux.getDouble()) {
										
										addToData(data2, data, g);		
										
									}
								}
							}
					//	this.data = data2;
					}
					
					
					
					else if(aux.getMetrica().equals("CYCLO")) { //metric is CYCLO
						

							if(operator.equals("<")) { //operator is <
							
								for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
									if(Integer.parseInt(data.get(g+5)) < aux.getDouble()) {
										
										addToData(data2, data, g);		
										
									}
								}
							}
							
							if (operator.equals(">")) { //operator is >
								
								for(int g = 0; g < data.size(); g+=columnNames2.length) {
									
									if(Integer.parseInt(data.get(g+5)) > aux.getDouble()) {
										
										addToData(data2, data, g);		
										
									}
								}
							}
							if (operator.equals("=")) { //operator is =
								
								for(int g = 0; g < data.size(); g+=columnNames2.length) {
									
									if(Integer.parseInt(data.get(g+5)) == aux.getDouble()) {
										
										addToData(data2, data, g);		
										
									}
								}
							}
					//	this.data = data2;
					}
					
					
					
					
					else if(aux.getMetrica().equals("ATFD")) { //metric is ATFD
						

						if(operator.equals("<")) { //operator is <
						
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
								if(Integer.parseInt(data.get(g+6)) < aux.getDouble()) {
									
									addToData(data2, data, g);		
									
								}
							}
						}
						
						if (operator.equals(">")) { //operator is >
							
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
								if(Integer.parseInt(data.get(g+6)) > aux.getDouble()) {
									
									addToData(data2, data, g);		
									
								}
							}
						}
						if (operator.equals("=")) { //operator is =
							
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
								if(Integer.parseInt(data.get(g+6)) == aux.getDouble()) {
									
									addToData(data2, data, g);		
									
								}
							}
						}
						//this.data = data2;
					}
					
					
					
					
					else if(aux.getMetrica().equals("LAA")) { //metric is LAA
						
						if(operator.equals("<")) { //operator is <
							
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
								if((int) Math.round(Double.parseDouble(data.get(g+7))) < aux.getDouble()) {
									
									addToData(data2, data, g);		
									
								}
							}
						}
						
						if (operator.equals(">")) { //operator is >
							
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
								if((int) Math.round(Double.parseDouble(data.get(g+7))) > aux.getDouble()) {
									
									addToData(data2, data, g);		
									
								}
							}
						}
						if (operator.equals("=")) { //operator is =
							
							for(int g = 0; g < data.size(); g+=columnNames2.length) {
								
								if( (int) Math.round (Double.parseDouble(data.get(g+7)) ) == aux.getDouble()) {
							
									addToData(data2, data, g);		
									
								}
							}
						}
						//this.data = data2;
					}
					
					
				//	data=data2;
				}
				
				data=data2;
				System.out.println("Tamanho do data: " + data.size() / 12 + " linhas");
				//System.out.println("Tamanho do data2: " + data2.size() / 12 + " linhas");
				//data2.clear();
			
			}
		
 			
			public void updateData2(int valor) {
				
				clearTable();
				ArrayList<String> data2 = new ArrayList<String>();
				
				if(valor == 1) { //iPlasma
					
					data2.clear();
					
					for(int g = 0; g < data.size(); g+=columnNames2.length) {
						
						if(data.get(g+9).equals("true")) {
							
							addToData(data2, data, g);
						}
					}
					
					data = data2;
				}
				
				if(valor == 2) { //PMD
					
					data2.clear();
					
					for(int g = 0; g < data.size(); g+=columnNames2.length) {
						
						if(data.get(g+10).equals("true")) {
							
							addToData(data2, data, g);
						}
					}
					
					data = data2;
					
				}
			}
			
			
 			public void resetExcel() {
 				
 				data.clear();
				
				while (model.getRowCount()>0) {
		             model.removeRow(0);
				}
				
				try {
					importarExcel(path);
				} catch (InvalidFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
 			}
 			
 			
 			public int[] contadores(int selecionado){
 				
 				int[] resposta = new int[4];
 				
 				int DCI_0 = 0;
 				int DII_1 = 0;
 				int ADCI_2 = 0;
 				int ADII_3 = 0;
 				
 				//iPlasma
 				if (selecionado == 1) {
 					
 					for(int g = 0; g < data.size(); g+=columnNames2.length) {
						
						if(data.get(g+9).equals("true")) { //iPlasma = true
							
							//System.out.println("entrei");
							
							if(data.get(g+8).equals("true")) { //is_long_method = true
								
								DCI_0++;
								
							}
							if(data.get(g+8).equals("false")) { //is_long_method = false
								
								DII_1++;
							} 	
						}
						if(data.get(g+9).equals("false")) { //iPlasma = false
							
							if(data.get(g+8).equals("true")) { //is_long_method = true
								
								ADII_3++;
								
							}
							if(data.get(g+8).equals("false")) { //is_long_method = false
								
								ADCI_2++;
							} 	
						}
					}	
 				}
 				
 				//PMD
 				if (selecionado == 2) {
 					
 					for(int g = 0; g < data.size(); g+=columnNames2.length) {
						
						if(data.get(g+10).equals("true")) { //PMD = true
							
							if(data.get(g+8).equals("true")) { //is_long_method = true
								
								DCI_0++;
								
							}
							if(data.get(g+8).equals("false")) { //is_long_method = false
								
								DII_1++;
							} 	
						}
						if(data.get(g+10).equals("false")) { //PMD = false
							
							if(data.get(g+8).equals("true")) { //is_long_method = true
								
								ADII_3++;
								
							}
							if(data.get(g+8).equals("false")) { //is_long_method = false
								
								ADCI_2++;
							} 	
						}
					}	
 				}
 				
 				
 				//rules user created
 				if (selecionado > 2) {
 					
 					Regra aux = regras.get(selecionado - 3);
 					
 					if(aux.getMetrica().equals("LOC") || aux.getMetrica().equals("CYCLO")) {
 						
 						for(int g = 0; g < data.size(); g+=columnNames2.length) {
 							
 							if(data.get(g+8).equals("true")) { //is_long_method = true
 	 							
 								DCI_0++;
 	 						}
 							
 							if(data.get(g+8).equals("false")) {//is_long_method = false
 								
 								DII_1++;
 							}
 						}
 					}
 					
 					if(aux.getMetrica().equals("ATFD") || aux.getMetrica().equals("LAA")) {
 						
 						for(int g = 0; g < data.size(); g+=columnNames2.length) {
 							
 							if(data.get(g+11).equals("true")) { //is_feature_envy = true
 	 							
 								DCI_0++;
 	 						}
 							
 							if(data.get(g+8).equals("false")) {//is_feature_envy = false
 								
 								DII_1++;
 							}
 						}
 					}
 					
 				}
 				
 				resposta[0] = DCI_0;
 				resposta[1] = DII_1;
 				resposta[2] = ADCI_2;
 				resposta[3] = ADII_3;
 				
 				return resposta;
 			}
 			
 			
 			public void addContent() throws InvalidFormatException, IOException {
				
				frame_principal.setSize(1400, 700);
				frame_principal.setLocation(100, 100);
				frame_principal.setLayout(new BorderLayout());
				frame_principal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				erroDialog = new JDialog(frame_principal, "Erro");
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
				
				
				frame_principal.add(scroll);
				scroll.setBounds(20,20,300,300);
				
				
				//Painel Sul
				JPanel hi = new JPanel();
				frame_principal.add(hi, BorderLayout.SOUTH);
				hi.setLayout(new FlowLayout());
				JButton verExcel = new JButton("Show Excel based on rules");
				JButton definirRegra = new JButton("Add Rule");
				JButton escolherRegra = new JButton("Detect Code Smells");
				JButton exit = new JButton ("Exit");
				JButton showRules = new JButton("Existing Rules");
				JButton deleteRule = new JButton("Delete Rule");
				JButton resetExcel = new JButton("Reset Excel");
				JButton dataSize = new JButton("Data Size");
				JButton deleteRules = new JButton("Delete all Rules");
				JButton evaluateQuality = new JButton("Evaluate Quality");
				
				
				hi.add(verExcel);
				hi.add(resetExcel);
				
				hi.add(definirRegra);
				hi.add(deleteRules);
				
				hi.add(escolherRegra);	
				hi.add(evaluateQuality);
				
				hi.add(showRules);
				hi.add(dataSize);
				hi.add(deleteRule);
				hi.add(exit);
				
				
				
				showRules.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
					
						if(regras.isEmpty())
							System.out.println("Não há regras criadas");
						
						for(int i = 0; i < regras.size(); i++) {
							
							System.out.println("REGRA Nº " + i + ":");
							System.out.println(regras.get(i).getMetrica());
							System.out.println(regras.get(i).getOperator());
							System.out.println(regras.get(i).getDouble());
						}
						
					}
					
					
					
				});
			
				
				verExcel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						clearTable();
						updateData(regras);
						showExcel();
							
					}
				
				});
				
				
				definirRegra.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						final Regra regra = new Regra();
						
						final JComboBox cb;
						final JComboBox cb2;
						final String numero;
						
						
						frame_setRule= new JFrame("Set a Rule");
						frame_setRule.setSize(800, 500);
						frame_setRule.setLocation(100, 100);
						frame_setRule.setLayout(new BorderLayout());
						frame_setRule.setVisible(true);

						JPanel buttonPane= new JPanel();
						JPanel fieldsPanel= new JPanel();
						JLabel metrics=new JLabel("Metrics");
						JLabel operator= new JLabel("Operator");
						JLabel number= new JLabel("Number");
						
						//First ComboBox - Métrica
						String [] options= {"Chosse an option", "LOC", "CYCLO", "ATFD", "LAA"};
						cb= new JComboBox(options);
						
						
						//Second ComboBox - Operador
						String [] options2= {"Chosse an option", ">", "<", "="};
						cb2= new JComboBox(options2);
						
						
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
						
						
						frame_setRule.add(fieldsPanel, BorderLayout.PAGE_START);
						frame_setRule.add(buttonPane, BorderLayout.PAGE_END);
						frame_setRule.pack();
						frame_setRule.setVisible(true);
						
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
//	    							if(isFloat(numero)==false){
//	    								erro.setText("Verifique o numero escrito");
//	    								erroDialog.setVisible(true);
//	    							}else {
//	    								System.out.println(numero);
	    								//numero = text.getText();
	    								double doub = Integer.parseInt(text.getText());
	    								regra.setDouble(doub);
	    							
	    					//		}
	    						
	    						regras.add(regra);
	    						updateData(regras);
	    						showExcel();
	    						frame_setRule.dispose();
	    							
	    					}
	    					
	    				});
						
						s2.addActionListener(new ActionListener(){
	    					
	    					@Override
	    					public void actionPerformed(ActionEvent e) {
	    						
	    						frame_setRule.dispose();
	    						
	    					}
	    					
	    				});
					
					}	
				});
				
				
				exit.addActionListener(new ActionListener(){
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						
						//clearTable();
						//frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
						frame_principal.dispose();
						
					}
					
				});

				
				escolherRegra.addActionListener(new ActionListener() {
                   
					@Override
                    public void actionPerformed(ActionEvent e) {
                       
						frame_codeSmells = new JFrame("Detect Code Smells");
						frame_codeSmells.setSize(1000, 700);
						frame_codeSmells.setLocation(100, 100);
						frame_codeSmells.setLayout(new BorderLayout());
						frame_codeSmells.setVisible(true);
                       
                        JPanel buttonPane= new JPanel();
                        JPanel fieldsPanel= new JPanel();
                        JPanel newP= new JPanel();
                        JLabel rule=new JLabel("Existing Filter");
                        JLabel ruleNumber= new JLabel("Created Rule ");
                        JLabel note = new JLabel("Remember to Reset Excel before choosing the filter or rule");
                        
                        String [] options= {"Choose an option", "iPlasma", "PMD"};
                        final JComboBox cb= new JComboBox(options);
                       
                        ArrayList<String> auxiliar= new ArrayList<String>();
                        auxiliar.add("Choose an option");
                        
                        for(int i = 0; i < regras.size(); i++) {
                        	Regra auxi = regras.get(i);
                        	
                        	String aux = String.valueOf(i) + ": " + auxi.getMetrica() + " " + auxi.getOperator() + " " + String.valueOf(auxi.getDouble());
                        	auxiliar.add(aux);
                        	
                        }
                        
                        final JComboBox<String> cb2 = new JComboBox<String>(new Vector<String>(auxiliar));
                        
                        JButton s2= new JButton("Cancel");
                        JButton ok1= new JButton("OK");
                        JButton ok2= new JButton("OK");
                        
                        fieldsPanel.setLayout(new FlowLayout());
                        buttonPane.setLayout(new FlowLayout());
                        fieldsPanel.add(rule);
                        fieldsPanel.add(cb);
                        fieldsPanel.add(ok1);
                        newP.add(ruleNumber);
                        newP.add(cb2);
                        newP.add(ok2);
                        buttonPane.add(note);
                        buttonPane.add(s2);
                        frame_codeSmells.add(fieldsPanel, BorderLayout.NORTH);
                        frame_codeSmells.add(newP, BorderLayout.CENTER);
                        frame_codeSmells.add(buttonPane, BorderLayout.PAGE_END);
                        frame_codeSmells.pack();
                        frame_codeSmells.setVisible(true);
                        
                        //Press OK normal Rule
                        ok1.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								int selected = cb.getSelectedIndex();
								
								//iPlasma or PMD
								if(selected == 1 || selected == 2) {
									
									updateData2(selected);
									
								}
								showExcel();
								frame_codeSmells.dispose();
							}
						});
                        
                        
                        //Press OK rule user created
                        ok2.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								int selected = cb2.getSelectedIndex();
								//System.out.println("Selected: " + selected);
								Regra aux = regras.get(selected - 1);
								ArrayList<Regra> auxi= new ArrayList<Regra>();
								auxi.clear();
								auxi.add(aux);
								resetExcel();
								updateData(auxi);
								showExcel();
								frame_codeSmells.dispose();
								
							}
						});
                        
                        s2.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {

								frame_codeSmells.dispose();
								
							}
						});
                    }
                });

				
				deleteRule.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						  frame_deleteRule= new JFrame("Delete Rule");
						  frame_deleteRule.setSize(800, 700);
						  frame_deleteRule.setLocation(100, 100);
						  frame_deleteRule.setLayout(new BorderLayout());
						  frame_deleteRule.setVisible(true);

	                      JPanel buttonPane= new JPanel();
	                      JPanel fieldsPanel= new JPanel();
	                      

	                      ArrayList<String> auxiliar= new ArrayList<String>();
	                        
	                      auxiliar.add("Choose an option");
	                        
	                        for(int i = 0; i < regras.size(); i++) {
	                        	
	                        	Regra auxi = regras.get(i);
	                        	
	                        	String aux = String.valueOf(i) + ": " + auxi.getMetrica() + " " + auxi.getOperator() + " " + String.valueOf(auxi.getDouble());
	                        	auxiliar.add(aux);
	                        	
	                        }
	                        
	                      final JComboBox<String> ruleNumber = new JComboBox<String>(new Vector<String>(auxiliar));

	                      //JTextField text = new JTextField("");

	                      JButton s = new JButton("Delete");
	                      JButton cancel = new JButton("Cancel");


	                      fieldsPanel.setLayout(new BoxLayout(fieldsPanel, BoxLayout.PAGE_AXIS));
	                      buttonPane.setLayout(new FlowLayout());

	                      fieldsPanel.add(ruleNumber);
	                      buttonPane.add(s);
	                      buttonPane.add(cancel);
	
	                      frame_deleteRule.add(fieldsPanel, BorderLayout.PAGE_START);
	                      frame_deleteRule.add(buttonPane, BorderLayout.PAGE_END);
	                      frame_deleteRule.pack();
	                      frame_deleteRule.setVisible(true);
	                      
	                   
	                      s.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								int toDelete = ruleNumber.getSelectedIndex() - 1;
								System.out.println(toDelete);
								regras.remove(toDelete);
								updateData(regras);
								showExcel();
								frame_deleteRule.dispose();
								
							}
						 });
	                      
	                      cancel.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								frame_deleteRule.dispose();
								
							}
						});
	                      
	                      
						
						
					}
					
					
				});
			
				
				resetExcel.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						resetExcel();
						showExcel();
						
					}
					
					
				});
			
				
				dataSize.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						System.out.println("Tamanho atual do data: " + data.size()/12);
					}
					
					
				});
			
				
				deleteRules.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						regras.clear();
						data.clear();
						updateData(regras);
						showExcel();
						
					}
				});
 			
				
				evaluateQuality.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						frame_evaluteQuality = new JFrame("Evaluate Quality");
						frame_evaluteQuality.setSize(1000, 700);
						frame_evaluteQuality.setLocation(100, 100);
						frame_evaluteQuality.setLayout(new BorderLayout());
						frame_evaluteQuality.setVisible(true);
                        
						frame_resultados = new JFrame("Evaluate Quality");
						frame_resultados.setSize(200, 200);
						frame_resultados.setLocation(100, 100);
						frame_resultados.setLayout(new FlowLayout());
                        
                        final JPanel principal = new JPanel();
                        principal.setLayout(new BorderLayout());
      
                        JPanel buttonPane= new JPanel();
                        JPanel fieldsPanel= new JPanel();
                        JPanel results= new JPanel();
                        JLabel filterOrRule=new JLabel("Filter or Rule");
                        JLabel note = new JLabel("Remember to Reset Excel before choosing the filter or rule");
                      
                        lista_modelo = new DefaultListModel<String>();

                        ArrayList<String> auxiliar= new ArrayList<String>();
                        
                        auxiliar.add("Choose an option");
                        auxiliar.add("iPlasma");
                        auxiliar.add("PMD");
                        
                        for(int i = 0; i < regras.size(); i++) {
                        	Regra auxi = regras.get(i);
                        	
                        	String aux = String.valueOf(i) + ": " + auxi.getMetrica() + " " + auxi.getOperator() + " " + String.valueOf(auxi.getDouble());
                        	auxiliar.add(aux);	
                        }
                        
                        final JComboBox<String> cb = new JComboBox<String>(new Vector<String>(auxiliar));
						
                        JButton cancel= new JButton("Cancel");
                        JButton ok= new JButton("OK");
                        
                        fieldsPanel.setLayout(new FlowLayout());
                        results.setLayout(new BoxLayout(results, BoxLayout.PAGE_AXIS));
                        buttonPane.setLayout(new FlowLayout());
                        fieldsPanel.add(filterOrRule);
                        fieldsPanel.add(cb);
                        fieldsPanel.add(ok);
                        
                        buttonPane.add(note);
                        buttonPane.add(cancel);
              
                        
                        frame_evaluteQuality.add(fieldsPanel, BorderLayout.NORTH);
                        frame_evaluteQuality.add(buttonPane, BorderLayout.PAGE_END);
                        frame_evaluteQuality.pack();
                        frame_evaluteQuality.setVisible(true);
                        

                        cancel.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								frame_evaluteQuality.dispose();
								
							}
						});
                        
                        ok.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								int[] resultados = contadores(cb.getSelectedIndex());
								
								lista_modelo.clear();
								lista_modelo.addElement("Here are the results:");
								lista_modelo.addElement("DCI: " + resultados[0]);
								lista_modelo.addElement("DII: " + resultados[1]);
								lista_modelo.addElement("ADCI: " + resultados[2]);
								lista_modelo.addElement("ADII: " + resultados[3]);
								
								JList<String> lista_resultados = new JList<String>(lista_modelo); 
							
								principal.add(lista_resultados);
								frame_resultados.add(principal);

								frame_evaluteQuality.dispose();
								frame_resultados.setVisible(true);
							
							}
						});
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



