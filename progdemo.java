package mini;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class progdemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	            MClass mm=new MClass();
	            try {
	            	prog.setVisible(false);
	            	mm.main(null);
	            	
	            }
	            catch(Exception ex) {}
	        }  
	    });  
		prog.add(l1);
		prog.add(l2);
		prog.add(l3);
		prog.add(b);
	}

}
