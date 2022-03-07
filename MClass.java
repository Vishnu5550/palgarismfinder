package mini;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MClass {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		JFrame prog=new JFrame("String Palgarism Finder");
		prog.setLayout(null);
		prog.setSize(700,300);
		prog.setVisible(true);
		JLabel l1,l2,l3;  
	    l1=new JLabel("Welcome To String Palgarism Finder!");  
	    l1.setBounds(50,50, 400,40);  
	    l2=new JLabel("1.  First You Choose Which Folder You Have Multiple Copied Files to Check.");  
	    l2.setBounds(50,100, 650,40);  
		l3=new JLabel("2.  Second You Choose the txt to compare with all files in selected folder");
		l3.setBounds(50, 150, 650, 40);
		JButton b=new JButton("Get Started>>");  
	    b.setBounds(400,200,130,30);  
	    b.addActionListener(new ActionListener(){  
	public void actionPerformed(ActionEvent e){  
//	            MClass mm=new MClass();
	            try {
	            	prog.setVisible(false);
//	            	mm.main(null);
	            	MClass s=new MClass();
	            	s.func();
	            	prog.dispose();
	            
	            }
	            catch(Exception ex) {}
	        }  
	    });  
		prog.add(l1);
		prog.add(l2);
		prog.add(l3);
		prog.add(b);
		
	}
	public void func() throws IOException {
		String ptrnLine, textLine,inpLine,sFilePath,srcLine;
		String input;
		int srcLineIndex=1, inpLineIndex=1;
		LCSS lcssComponent; 
		KMP kmpComponent;
		BMSubStr bmComponent;
		Naive nsmComponent;
		RabinKarp rkComponent;
		int inputLen,srcLen,patterntextLength;
		double lcssSimRatio = 0, kmpSimRatio = 0, boyreMooreSimRatio = 0, stringNaSimRatio = 0;
		int rkNumberOfMatches;
		int fullTextLength=0, fullPatternLength=0;
		int mainTimeCount= 0, lcssComponentCount=0,kmpComponentCount=0, rkComponentCount=0, bmComponentCount=0, naiveComponentCount=0;
		boolean rkPlagarismStatus = false;
		//Source folder for the genuine files (pool) 
		String checkfold="";
		JFileChooser fold=new JFileChooser();
		fold.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int op=fold.showOpenDialog(null);
		if(JFileChooser.APPROVE_OPTION==op) {
			File f=fold.getSelectedFile();
			checkfold=f.getPath();
		}
		System.out.println(checkfold);
		String checkfile="";
		JFileChooser fill=new JFileChooser();
		FileNameExtensionFilter filt=new FileNameExtensionFilter("TEXT FILES","txt","text");
		fill.setFileFilter(filt);
//		fold.setFileSelectionMode();
		int opf=fill.showOpenDialog(null);
		if(JFileChooser.APPROVE_OPTION==opf) {
			File f=fill.getSelectedFile();
			checkfile=f.getPath();
		}
		System.out.println(checkfile);
		final File folder = new File(checkfold);
		File fileLcss = new File("lcss.txt"); // lcss result to be written in lcss.txt
		File fileKmp = new File("kmp.txt"); // kmp result to be written in kmp.txt
		File fileNaive = new File("naive.txt"); // naive string comparison  result to be written in naive.txt
		File fileBM = new File("boyreMoore.txt"); // Boyes Moore result to be written in boyreMoore.txt
		File fileRK = new File("rk.txt"); //Rabin Karp result of the plagarism written in rk.txt
		int coun = 0;
		// old result files deleted for the new result files
		fileLcss.delete();
		fileKmp.delete();
		fileNaive.delete();
		fileBM.delete();
		fileRK.delete();
		
		FileWriter flcss = new FileWriter("lcss.txt", false); 
		FileWriter fkmp = new FileWriter("kmp.txt", false); 
		FileWriter fnav = new FileWriter("lcss.txt", false); 
		FileWriter fboy = new FileWriter("boyreMoore.txt", false); 
		FileWriter frk = new FileWriter("rk.txt", false); 
        PrintWriter plc1 = new PrintWriter(flcss, false);
        PrintWriter plc2 = new PrintWriter(fkmp, false);
        PrintWriter plc3 = new PrintWriter(fnav, false);
        PrintWriter plc4 = new PrintWriter(fboy, false);
        PrintWriter plc5 = new PrintWriter(frk, false);
        plc1.flush();
        plc2.flush();
        plc3.flush();
        plc4.flush();
        plc5.flush();
        
        flcss.close();
		
		FileWriter outLcssFile = new FileWriter("lcss.txt", true);
		FileWriter outKmpFile = new FileWriter("kmp.txt", true);
		FileWriter outBoyreMooreFile = new FileWriter("boyreMoore.txt", true);
		FileWriter outNaiveFile = new FileWriter("naive.txt", true);
		FileWriter outRkFile = new FileWriter("rk.txt", true);
		int nooffiles=folder.list().length;
		System.out.println(nooffiles);
		JFrame prog1=new JFrame("Processing...");
		prog1.setLayout(null);
		prog1.setSize(500,500);
		
		JProgressBar jb=new JProgressBar(0,100);    
		jb.setBounds(40,40,250,50);         
		jb.setValue(0);    
		jb.setStringPainted(true);    
		prog1.add(jb);
		prog1.setVisible(true);
		float v=0;
		float t=100;
		float progvalue=t/nooffiles;
		jb.setValue((int)v);
		// reading the files from the pool for the plagiarism 
		for (final File fileEntry : folder.listFiles()) {
			v=v+progvalue;
			sFilePath = fileEntry.getPath();
			srcLineIndex=1;
			File sourceFile = new File(sFilePath);
			File inputFile = new File(checkfile); // the source file to be examined for plagiarism
			
			@SuppressWarnings("resource")
			BufferedReader sReader = new BufferedReader( new FileReader(sourceFile));
			while((srcLine = sReader.readLine())!=null){

				BufferedReader reader = new BufferedReader( new FileReader(inputFile));
				inpLineIndex=1;
				fullTextLength = fullTextLength+srcLine.length();
				while((inpLine = reader.readLine())!=null){
					inputLen = inpLine.length();
					srcLen = srcLine.length();
				
					if(inputLen>0 && srcLen>0) // msking sure that the strings to be compared have non-zero length
					{
						if(srcLen>inputLen)
						{   textLine = srcLine;
						ptrnLine = inpLine;
						}
						else
						{  textLine = inpLine;
						ptrnLine = srcLine;
						}

						patterntextLength = ptrnLine.length();
						
						//fullTextLength = fullTextLength+textLine.length();
						if(coun<1)
						{
						fullPatternLength = fullPatternLength+ ptrnLine.length();
						}
						//Running LCSS component
						lcssComponent = new LCSS(ptrnLine,textLine);
	       				lcssComponent.computeLCSS();
	       				 if(patterntextLength!=0)
						{ lcssSimRatio = (lcssComponent.getLcssLen()/(double)(patterntextLength));
	       				}
//	       				System.out.println("LCSS Result");
//	       				System.out.println("Similarity ratio = "+lcssSimRatio*100.000+" Line Number of the input file= "+inpLineIndex+
//	    				" Line Number of the source file "+fileEntry.getName()+ " = "+srcLineIndex);
//	       				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
	       				
	       				    PrintWriter outPLcssFile = new PrintWriter(outLcssFile);
	       				    	if(lcssSimRatio>0.60)
	       				    	{ outPLcssFile.append("Line "+inpLineIndex + " of the input file has plagarised " +lcssSimRatio*100.000+
	       				    			"% from line "+srcLineIndex +" of the source file "+fileEntry.getName()+"\n");
	       				    	}
	       				    	
	       				   //Computing Lcss Time Count
                                lcssComponentCount =lcssComponentCount+ lcssComponent.getTimeCount();
//                                System.out.println("The amount of time LCSS took for a text lenghth "+textLine.length()+" and pattern length " +ptrnLine.length()+" is "+
//                                                lcssComponentCount);
                                mainTimeCount = lcssComponentCount+mainTimeCount;

					//Running KMP component
						kmpComponent = new KMP();
						 if(patterntextLength!=0)
						{ kmpSimRatio= (kmpComponent.searchSubString(textLine, ptrnLine)/(double)(patterntextLength));
						}
//						System.out.println("KMP Result");
//						System.out.println("Similarity ratio = "+kmpSimRatio*100.000+" Line Number of the input file= "+inpLineIndex+
//								" Line Number of the source file "+fileEntry.getName()+ " = "+srcLineIndex);
//						System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
						PrintWriter outPKmpFile = new PrintWriter(outKmpFile);
   				    	if(kmpSimRatio>0.60)
   				    	{ outPKmpFile.append("Line "+inpLineIndex + " of the input file has plagarised " +kmpSimRatio*100.000+
   				    			"% from line "+srcLineIndex +" of the source file "+fileEntry.getName()+"\n");
   				    	}

   				     //Computing KMP Time Count
   				    	kmpComponentCount = kmpComponentCount+kmpComponent.getTimeCount();
//                        System.out.println("The amount of time KMP took for a text lenghth "+textLine.length()+" and pattern length " +ptrnLine.length()+" is "+
//                        		          kmpComponentCount);
                        mainTimeCount = kmpComponentCount+mainTimeCount;

						//Running boyer moore algorithm
						bmComponent = new BMSubStr(ptrnLine,textLine);
						if(patterntextLength!=0)
						{
							boyreMooreSimRatio = (bmComponent.BMSimilarity()/(double)(patterntextLength));
						}
//						System.out.println("boyer moore Result");
//						System.out.println("Similarity ratio = "+boyreMooreSimRatio*100.000+" Line Number of the input file= "+inpLineIndex+
//								" Line Number of the source file "+fileEntry.getName()+ " = "+srcLineIndex);
//						System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
						
						PrintWriter outPBoyreMooreFile = new PrintWriter(outBoyreMooreFile);
   				    	if(boyreMooreSimRatio>0.60)
   				    	{ outPBoyreMooreFile.append("Line "+inpLineIndex + " of the input file has plagarised " +boyreMooreSimRatio*100.000+
   				    			"% from line "+srcLineIndex +" of the source file "+fileEntry.getName()+"\n");
   				    	}
						
   				    	
   				         //Computing boyer moore Time Count
   				    	bmComponentCount = bmComponentCount+bmComponent.getTimeCount();
                        
                        mainTimeCount = bmComponentCount+mainTimeCount;  
//                        System.out.println("The amount of time boyer moore took for a text lenghth "+textLine.length()+" and pattern length " +ptrnLine.length()+" is "+
//                				bmComponentCount);

						
						//Running Naive String Matching Algorithm
						nsmComponent= new Naive();
						if(patterntextLength!=0)
						{
							stringNaSimRatio = (nsmComponent.search(ptrnLine, textLine)/(double)(patterntextLength));
						}
//						System.out.println("Naive String Matching Algorithm Result");
//						System.out.println("Similarity ratio = "+stringNaSimRatio*100.000+" Line Number of the input file= "+inpLineIndex+
//								" Line Number of the source file "+fileEntry.getName()+ " = "+srcLineIndex);
//						System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
						PrintWriter outPNaiveFile = new PrintWriter(outNaiveFile);
   				    	if(stringNaSimRatio>0.60)
   				    	{ outPNaiveFile.append("Line "+inpLineIndex + " of the input file has plagarised " +stringNaSimRatio*100.000+
   				    			"% from line "+srcLineIndex +" of the source file "+fileEntry.getName()+"\n");
   				    	}
   				    	
   				      //Computing Naive String Matching Time Count
   				    	naiveComponentCount =naiveComponentCount+ nsmComponent.getTimeCount();
   			
//                        System.out.println("The amount of time Naive String took for a text lenghth "+textLine.length()+" and pattern length " +ptrnLine.length()+" is "+
//                        		naiveComponentCount);
                        mainTimeCount = naiveComponentCount+mainTimeCount; 
                        
                        
   				    	//Running Rabin Karp Algorithm
						rkComponent = new RabinKarp();
						if(patterntextLength!=0)
						{
							rkNumberOfMatches = rkComponent.search(ptrnLine,textLine);
							if(rkNumberOfMatches>0)
							{  
								rkPlagarismStatus = true;
							}
							else
							{
								rkPlagarismStatus =false;
							}
							if(rkPlagarismStatus)
							{   
//								System.out.println("Rabin Karp Algorithm Result");
//								System.out.println(" Line Number of the input file= "+inpLineIndex+ " is plagarised from" +
//									" Line Number of the source file "+fileEntry.getName()+ " = "+srcLineIndex+" Number of times string matched was "+rkNumberOfMatches);
//								System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
							 PrintWriter outPRkFile = new PrintWriter(outRkFile);
	   				    	 outPRkFile.append("Line "+inpLineIndex + " of the input file has plagarised from line "+srcLineIndex +" of the source file "+fileEntry.getName()+
	   				    			 " "+rkNumberOfMatches+" time string matching found\n");
							}
							 //Computing Naive String Matching Time Count
							rkComponentCount = rkComponentCount+rkComponent.getTimeCount();
//	                        System.out.println("The amount of time Rabin Karp took for a text lenghth "+textLine.length()+" and pattern length " +ptrnLine.length()+" is "+
//	                        		rkComponentCount);
	                        mainTimeCount = rkComponentCount+mainTimeCount;
	                        
						}
						inpLineIndex++;
					}
					
				}
				coun++;
				srcLineIndex++;
			}
			   
				  jb.setValue((int)v);    
				 
				  try{Thread.sleep(100);}catch(Exception e){}   
//		}
//			try{Thread.sleep(150);}catch(Exception e){}    
			System.out.println(v+"==="+progvalue);
			
		}
		if((int)v==100) {
			prog1.dispose();
		}
		outLcssFile.close();
		outKmpFile.close();
		outBoyreMooreFile.close();
		outNaiveFile.close();
		outRkFile.close();
		
		mainTimeCount = lcssComponentCount+kmpComponentCount+naiveComponentCount+bmComponentCount+rkComponentCount;
//		 System.out.println("The amount of time LCSS took for a text lenghth "+fullTextLength+" and pattern length " +fullPatternLength+" is "+
//                 lcssComponentCount);
//		 System.out.println("The amount of time KMP took for a text lenghth "+fullTextLength+" and pattern length " +fullPatternLength+" is "+
//		          kmpComponentCount);
//		System.out.println("The amount of time boyer moore took for a text lenghth "+fullTextLength+" and pattern length " +fullPatternLength+" is "+
//				bmComponentCount);
//
//        System.out.println("The amount of time Naive String took for a text lenghth "+fullTextLength+" and pattern length " +fullPatternLength+" is "+
//        		naiveComponentCount);
//		System.out.println("The amount of time Rabin Karp took for a text lenghth "+fullTextLength+" and pattern length " +fullPatternLength+" is "+
//        		rkComponentCount);
		System.out.println("The amount of total time  took for full Text length "+fullTextLength+" and full pattern length " +fullPatternLength+" is "+
         		mainTimeCount);
		
		stringPlagarismOutputUI uii=new stringPlagarismOutputUI();
		uii.main(null);
	}

}

