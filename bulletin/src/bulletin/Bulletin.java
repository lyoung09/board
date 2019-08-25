
package bulletin;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSetMetaData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.text.SimpleDateFormat;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Bulletin extends JFrame   {
    private JList<String> TitleList = new JList<String>();
    private JList<String> nickname = new JList<String>();
    private JList<String> ImpoL = new JList<String>();
    private DefaultListModel<String> TitleModel = new DefaultListModel<String>();
    private DefaultListModel<String> nicknameModel = new DefaultListModel<String>();
    private DefaultListModel<String> ImpoModel= new DefaultListModel<String>();
    private Vector<JTextField> v1 = new Vector<JTextField>();
    private Vector<JTextArea> v2 = new Vector<JTextArea>();
    private Vector<JTextField> v3 = new Vector<JTextField>();
    private Vector<JTextField> v4 = new Vector<JTextField>();
    private JTable table;
   
   
    private int index;
    
    public Bulletin() {
        this.setTitle("Java Notice-Board");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(getOwner());
        this.setBounds(250,50,40,200);
      
        
        InitialScreen is = new InitialScreen();
        setContentPane(is);
                       
        this.setResizable(false);
        this.setSize(500, 500);
        this.setVisible(true);
    }
   


    private class InitialScreen extends JPanel  {
        private JButton WriteBtn, DeleteBtn, ModifyBtn,SearchBtn;
        private JTextField Sear;
        public InitialScreen() {
           
            this.setLayout(null);
            this.setSize(500, 500);
            JLabel label = new JLabel("");
            label.setBounds(20, 20, 450, 500);
           
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            
         
     

            TitleList.setBounds(20, 70, 300, 345);
            
            nickname .setBounds(320, 70, 150, 345);    
           // nickname .setEnabled(false);
            
            add(TitleList);add(nickname ); add(ImpoL);
            
            
            Sear = new JTextField();
            Sear.setBounds(40, 430, 75, 25);
           

            SearchBtn = new JButton("검색");
            SearchBtn.setBounds(120, 430, 75, 25);
            
            
            WriteBtn = new JButton("쓰기");
            WriteBtn.setBounds(200, 430, 75, 25);
            
            ModifyBtn = new JButton("읽기  / 수정");
            ModifyBtn.setBounds(300, 430, 105, 25);
          
            DeleteBtn = new JButton("삭제");
            DeleteBtn.setBounds(420, 430, 70, 25);
            
            add(Sear);add(WriteBtn); add(ModifyBtn); add(DeleteBtn);add(SearchBtn);
            
            WriteBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {                 
                    Screen s = new Screen("New Writing");
                    setContentPane(s);
                }
            });
            
            ModifyBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(TitleList.isSelectionEmpty() == false) {
                        index = TitleList.getSelectedIndex();
                        Screen s = new Screen("Modify");
                        setContentPane(s);
                    }
                }
            });
            
            DeleteBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(TitleList.isSelectionEmpty() == false) {
                        int choice = JOptionPane.showConfirmDialog(null, "삭제?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                        if(choice == JOptionPane.YES_OPTION) {
                            v1.remove(TitleList.getSelectedIndex());
                            v2.remove(TitleList.getSelectedIndex());
                            
                            nicknameModel.remove(TitleList.getSelectedIndex());
                            nickname .setModel(nicknameModel);
                                
                            TitleModel.remove(TitleList.getSelectedIndex());
                            TitleList.setModel(TitleModel);
                        }
                        else {
                            return;
                        }
                    }
                }
            });
         
            //7번
    		SearchBtn.addActionListener(new ActionListener()
    		{
    			@Override
    			public void actionPerformed(ActionEvent e)
    			{
    				
    				ListModel model = TitleList.getModel(); 
    				int index =TitleList.getSelectedIndex();
    				
    				ListModel model2 = nickname.getModel(); 
    				int index2 =nickname.getSelectedIndex();
    				
    				for(int i=0; i < model.getSize(); i++){ 
    				    Object o = model.getElementAt(i);
    				    Object o2 = model2.getElementAt(i);
    				   String s=Sear.getText();
    				
    				   if(s.equals(o)) {
					   JOptionPane.showMessageDialog(null, (i+1)+"번에 있습니다","<ID>", JOptionPane.ERROR_MESSAGE);
    				   }
    				   if(s.equals(o2)) {
						   JOptionPane.showMessageDialog(null, (i+1)+"번에 있습니다","<닉네임>", JOptionPane.ERROR_MESSAGE);
	    				   }
    				  if(!s.equals(o)&&!s.equals(o2)) {
    					 
    	    				  
    					   JOptionPane.showMessageDialog(null, "없습니다","검색 결과", JOptionPane.ERROR_MESSAGE);
    				   }

    				
    				} 
    			}
    		});
    	
        }
      
       
}



     
        
    private class Screen extends JPanel implements ItemListener  {
        private JLabel label;
        private JTextField Title;
        private JTextArea Contents;
        private JTextField nickName,Im;
        private JButton Save;
        private JButton Impo;
        private Error err = new Error();
        private JCheckBox jc;
        JRadioButton rb1;
        public Screen(String str) {
            this.setSize(500, 500);
            this.setLayout(null);
            
            label = new JLabel(str);
            label.setBounds(20, 5, 450, 30);
            label.setHorizontalAlignment(JLabel.CENTER);
            
            Title = new JTextField();
            Title.setBounds(20, 10, 450, 30);
           
           
            nickName = new JTextField();           
            nickName.setBounds(20, 40, 450, 30);
         
            if( !nicknameModel.isEmpty() ) {
    			String x = nicknameModel.get(nicknameModel.size() - 1);
    		
    			nickName.setText(x+"");
            }
         
            
            Contents = new JTextArea();
            Contents.setBounds(20, 75, 450, 350);
            Contents.setLineWrap(true);
            JScrollPane sp = new JScrollPane(Contents);
            sp.setBounds(20, 75, 450, 350);
            
            Save = new JButton("Save");
            Save.setBounds(this.getWidth()/4 + 140, 435, 80, 20);
            
            Impo = new JButton("☆");
            Impo.setBounds(this.getWidth()/2+ 140, 435, 140, 20);
            
            //jc = new JCheckBox("중요");
           // jc.setBounds(this.getWidth()/2+140, 435, 140, 20);
           
            rb1= new JRadioButton("중요");
            rb1.setBounds(this.getWidth()/2+140, 435, 140, 20);
            rb1.addItemListener(this);
            
            add(label); add(Title); add(nickName); add(sp); add(Save);add(rb1);
     

          
            
            if(label.getText().equals("New Writing")) {
                Save.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(Title.getText().length() == 0 || Contents.getText().length() == 0) {
                            err.errorMessage();
                            return;
                        }
                        
                        TitleModel.addElement(Title.getText());
                        TitleList.setModel(TitleModel);
                            
                        nicknameModel.addElement(nickName.getText());
                        nickname .setModel(nicknameModel);
                        
                        v1.add(Title);
                        v2.add(Contents);
                        v3.add(nickName);
                      
                        
                        
                        InitialScreen is = new InitialScreen();
                        setContentPane(is);
                    }
                });
            
            
            }
            
           
            if(label.getText().equals("Modify")) {
                Title.setText(v1.get(index).getText());
                Contents.setText(v2.get(index).getText());
                nickName.setText(v3.get(index).getText());
                Save.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(Title.getText().length() == 0 || Contents.getText().length() == 0) {
                            err.errorMessage();
                            return;
                        }
                        else {
                            
                            
                            TitleModel.set(index, Title.getText());
                            TitleList.setModel(TitleModel);
                            
                            nicknameModel.set(index, nickName.getText());
                            nickname .setModel(nicknameModel);
                            
                            v1.set(index, Title);                         
                            v2.set(index, Contents);
                            v3.set(index, nickName);
                          
                            
                            InitialScreen is = new InitialScreen();
                            setContentPane(is);
                        }
                    }
                });
            }
           
        }
        
        private class Error {
            public void errorMessage() {
                JOptionPane.showMessageDialog(null, "입력", "", JOptionPane.ERROR_MESSAGE);
            }
        }
     
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			Object ob1=e.getSource();
			ListModel model = TitleList.getModel(); 
			int index =TitleList.getSelectedIndex();
			
			    	
			nickname.setBackground(ob1==rb1 ? Color.CYAN:Color.WHITE);
			    
			    
			
			
				  
				  
			  
		}

   
        
      
    }
    		
  
                

             
   
 
    public static void main(String[] args) {
        new Bulletin();
    }

	
}