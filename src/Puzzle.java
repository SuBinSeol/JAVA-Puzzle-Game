import java.net.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.util.Date;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Puzzle extends JFrame implements ActionListener{
	Set set=new LinkedHashSet();//사용자
	Calendar StartTime;
	JPanel numPanel;
	JPanel Panel1;
	JButton[][] numButtons;
	JLabel label;
	JLabel timelabel;
	JMenuBar mb=new JMenuBar();
	JMenu mFile=new JMenu("File");
	JMenuItem Restart=new JMenuItem("Restart");
	JMenu Size=new JMenu("Size");
	JMenuItem Image=new JMenuItem("Image");
	JMenuItem Exit=new JMenuItem("Exit");
	JMenuItem S_three=new JMenuItem("3x3");
	JMenuItem S_five=new JMenuItem("5x5");
	JMenuItem S_seven=new JMenuItem("7x7");
	JFileChooser jfc = new JFileChooser();
	Timer timer;
	
	String name="",send="";
	boolean time=false;
	int size=0;
	ImageIcon changeIcon;
	private BufferedImage img,resized,gray;
    int pieceWidth,pieceHeight;
	Puzzle(String name) {
		super("Puzzle");
		this.name=name;
//		
//        try{
//            img = ImageIO.read(new File("3.jpg"));
//            gray=ImageIO.read(new File("gray.jpg"));
//            double ratio = 840 / (double) img.getWidth();
//            int h = (int) (img.getHeight() * ratio);
//            resized = resizeImage(img, 840, h, BufferedImage.TYPE_INT_ARGB);
//        } catch (IOException e){
//            System.out.println(e.getMessage());
//            System.exit(0);
//        }
//        changeIcon = new ImageIcon(resized);
//		JLabel lbl = new JLabel(changeIcon);
	    Panel1=new JPanel();
        //MyPanel Panel1 = new MyPanel();
	    Panel1.setLayout(new BorderLayout());
		label=new JLabel("name : "+name);
		timelabel=new JLabel("0:0:0");
		timelabel.setFont(timelabel.getFont().deriveFont(30.0f));
		label.setFont(label.getFont().deriveFont(25.0f));
		mFile.add(Restart);
		mFile.addSeparator();
		mFile.add(Size);
		mFile.addSeparator();
		mFile.add(Image);
		mFile.addSeparator();
		mFile.add(Exit);
		Size.add(S_three);
		Size.add(S_five);
		Size.add(S_seven);
		mb.add(mFile);
		Panel1.add("North",mb);
		Panel1.add("West",label);
		Panel1.add("East",timelabel);
		add("North",Panel1);
		//add("Center",lbl);
		Restart.addActionListener(this);
		Image.addActionListener(this);
		Exit.addActionListener(this);
		S_three.addActionListener(this);
		S_five.addActionListener(this);
		S_seven.addActionListener(this);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setBounds(300,200,840,630);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String s= e.getActionCommand();
		if(s.equals("3x3")||s.equals("5x5")||s.equals("7x7")){
            getContentPane().removeAll();
            if(s.equals("3x3")) {
            	size=3;
            	//division(816);
            	//S_three.setBackground(Color.lightGray);
            }else if(s.equals("5x5")) {
            	size=5;
            	//division(880);
            	//S_five.setBackground(Color.lightGray);
            }else {
            	size=7;
            	//division(910);
            	//S_seven.setBackground(Color.lightGray);
            }
            pieceWidth = resized.getWidth(null) / size;
            pieceHeight = resized.getHeight(null) / size;
            System.out.println("w="+pieceWidth+" h="+pieceHeight+" Allw="+resized.getWidth(null)+" Allh="+resized.getHeight(null));
            
            getContentPane().add(Pan());
            revalidate();
            repaint();
            
            Size.setEnabled(false);
            StartTime=Calendar.getInstance();
            Time();
		}else if(s.equals("Restart")){
			JOptionPane.showMessageDialog(null, "게임을 다시 시작합니다.","초기화",JOptionPane.PLAIN_MESSAGE);
			timer.stop();
			size=0;
        	StartTime=null;
            Size.setEnabled(true);
//            S_three.setBackground(new Color(238,238,238));
//            S_five.setBackground(new Color(238,238,238));
//            S_seven.setBackground(new Color(238,238,238));
            getContentPane().removeAll();
            getContentPane().add(menu());
            revalidate();
            repaint();
        }else if(s.equals("Image")) {
        	String path = "",name3="";
        	JFileChooser chooser = new JFileChooser();
        	int returnVal = chooser.showOpenDialog(this);
        	if(returnVal == JFileChooser.APPROVE_OPTION){
        		path = chooser.getSelectedFile().getPath();
        	    name3 = chooser.getSelectedFile().getName();
        	}
        	System.out.println(path);
        	System.out.println(name3);
            try{
                img = ImageIO.read(new File(path));
                gray=ImageIO.read(new File("gray.jpg"));
                double ratio = 840 / (double) img.getWidth();
                int h = (int) (img.getHeight() * ratio);
                System.out.println("H="+img.getHeight());
                resized = resizeImage(img, 840, 525, BufferedImage.TYPE_INT_ARGB);
            } catch (IOException e2){
                System.out.println(e2.getMessage());
                System.exit(0);
            }
            changeIcon = new ImageIcon(resized);
    		JLabel lbl = new JLabel(changeIcon);
            getContentPane().add(Pan1(lbl));
            revalidate();
            repaint();
        }
		else if(s.equals("Exit")){
        	JOptionPane.showMessageDialog(null, "게임을 종료합니다.","종료",JOptionPane.PLAIN_MESSAGE);
        	dispose();
        }else {
        	 if(size>0) {
        		 JButton button=(JButton)e.getSource();
        		 int n=PuzzleGame(button.getText());
        	 }else {
        		 JOptionPane.showMessageDialog(null, "size를 선택해주세요.");
        	 }
         }
	}
	public JPanel Pan1(JLabel lbl) {//판넬 위치 조정
		JPanel j=new JPanel();
	    JPanel jp=new JPanel();
	    j.setLayout(new BorderLayout());
	    jp.setLayout(new BorderLayout());
	    
	    j.add("North",mb);
	    j.add("West",label);
	    j.add("East",timelabel);
	    jp.add("North",j);
	    jp.add("Center",lbl);
	    return jp;
	}
	public JPanel Pan() {//판넬 위치 조정
		JPanel j=new JPanel();
	    JPanel jp=new JPanel();
	    j.setLayout(new BorderLayout());
	    jp.setLayout(new BorderLayout());
	    
	    j.add("North",mb);
	    j.add("West",label);
	    j.add("East",timelabel);
	    jp.add("North",j);
	    jp.add("Center",arrnum());
	    return jp;
	}
	private BufferedImage resizeImage(BufferedImage originalImage, int width,int height, int type) {//이미지 사이즈 고정
		var resizedImage = new BufferedImage(width, height, type);
		var g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		
		return resizedImage;
	}
	public void division() {
        try{
            img = ImageIO.read(new File("3.jpg"));
            gray=ImageIO.read(new File("gray.jpg"));
            double ratio = 840 / (double) img.getWidth();
            int h = (int) (img.getHeight() * ratio);
            System.out.println("H="+h);
            resized = resizeImage(img, 840, h, BufferedImage.TYPE_INT_ARGB);
        } catch (IOException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
        changeIcon = new ImageIcon(resized);
		JLabel lbl = new JLabel(changeIcon);
	}
	public JPanel arrnum() {//선택한 사이즈만큼 버튼 만들어 판넬에 붙임
		//size=num;
		BufferedImage new_buff;
		JPanel numPanel=new JPanel();
		numPanel.setLayout(new GridLayout(size,size));
		numButtons=new JButton[size][size];
		set.clear();
		while(set.size()<(numButtons.length*numButtons.length)) {//랜덤으로 넣음
			set.add((int)(Math.random()*(size*size))+1+"");
		}
//		int w=1;
//		while(set.size()<(numButtons.length*numButtons.length)) {//랜덤으로 넣음
//			set.add(w+"");
//			w++;
//		}
		Iterator it=set.iterator();
		int i=0;

		while(i<numButtons.length) {
			int j=0;
			int sy = i * pieceHeight;
		    while(j<numButtons[i].length) {
		    	String n=(String)it.next();
		    	int num1=Integer.parseInt(n);
                int sx = j * pieceWidth;
                int dx = (num1 % size) * pieceWidth;
                int dy = (num1 / size) * pieceHeight;
                
		    	if(n.equals(Integer.toString(size*size))) {
		    		numButtons[i][j]=new JButton("0");
		    		numButtons[i][j].setContentAreaFilled(false);
		    		//numButtons[i][j].setBorderPainted(false);
		    		
		    		new_buff = gray.getSubimage(0, 0, pieceWidth, pieceHeight);
		    		numButtons[i][j].setIcon(new ImageIcon(new_buff));
		    		numButtons[i][j].setPreferredSize(new Dimension(pieceWidth, pieceHeight));
		    		//numButtons[i][j].setSize(pieceWidth,pieceHeight);
		    	}else {
		    		numButtons[i][j]=new JButton(n);
		    		numButtons[i][j].setContentAreaFilled(false);
		    		//numButtons[i][j].setSize(pieceWidth,pieceHeight);
		    		//numButtons[i][j].setBorderPainted(false);
			        int x=num1/size,y=(num1%size)-1;
			        if(num1%size==0) {
			        	x-=1;
			        	y=size-1;
			        }
		    		new_buff = resized.getSubimage(pieceWidth*y, pieceHeight*x, pieceWidth, pieceHeight);
		    		
		    		var resizedImage = new BufferedImage(pieceWidth+3, pieceHeight, BufferedImage.TYPE_INT_ARGB);
		    		var g = resizedImage.createGraphics();
		    		g.drawImage(new_buff, 0, 0, pieceWidth+3, pieceHeight, null);
		    		numButtons[i][j].setIcon(new ImageIcon(resizedImage));
		    		numButtons[i][j].setPreferredSize(new Dimension(pieceWidth, pieceHeight));
		    		
		    		
		    		//numButtons[i][j].setSize(pieceWidth,pieceHeight);
		    	}
		        numButtons[i][j].addActionListener(this);
		        numPanel.add(numButtons[i][j]);
		        j++;
		    }
		    i++;
		}
		return numPanel;
	}
	public JPanel menu() {
		JPanel m=new JPanel();
		JPanel mp=new JPanel();
	    m.setLayout(new BorderLayout());
	    mp.setLayout(new BorderLayout());
	    timelabel.setText(Integer.toString(0));
	    m.add("North",mb);
	    m.add("West",label);
	    m.add("East",timelabel);
	    mp.add("North",m);
	    return mp;
	}
	public int PuzzleGame(String num) {
	    int a=0,b=0,c=0,number=0;
	    BufferedImage new_buff;
	   	Loop:for(a=0;a<size;a++) {
	    	for(b=0;b<size;b++) {
		    	if(numButtons[a][b].getText().equals(num)) {
		    		break Loop;
		    	}
	    	}
		}
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(numButtons[i][j].getText().equals("0")) {
					if(i==a||j==b) {
				    	if(i+1==a||i-1==a||j+1==b||j-1==b) {
				    		new_buff = gray.getSubimage(0, 0, pieceWidth, pieceHeight);
				    		numButtons[a][b].setIcon(new ImageIcon(new_buff));
				    		numButtons[a][b].setText(numButtons[i][j].getText());
				    		numButtons[i][j].setText(num);
					        int x=Integer.parseInt(num)/size,y=(Integer.parseInt(num)%size)-1;
					        if(Integer.parseInt(num)%size==0) {
					        	x-=1;
					        	y=size-1;
					        }
					        new_buff = resized.getSubimage(pieceWidth*y, pieceHeight*x, pieceWidth, pieceHeight);
				    		numButtons[i][j].setIcon(new ImageIcon(new_buff));
					    	i=a;
					    	j=b;
					   }
					}else {
						JOptionPane.showMessageDialog(null, "상하좌우 버튼만 누를 수 있습니다.");
					}
					break;
				}
			}
		}
		number=1;
    	c=0;
		for(int q=0;q<size;q++){
	    	for(int p=0;p<size;p++){
		    	if(numButtons[q][p].getText().equals(Integer.toString(number))) {
			    	c++;
			    	if(c==((size*size)-1)) {
			    		String t=playTime();
			    		MariaDB mdb=new MariaDB();
			    		Connection conn=null;
			    		conn=mdb.connectDB();
			    		PreparedStatement pstmt=null;
			    		try {
			    			pstmt = conn.prepareStatement("insert into ranking values(?,?)");
			    			pstmt.setString(1, name);
			    		     DateFormat df = new SimpleDateFormat("HH:mm:ss");
			    		     Date date = null;
			    		     date = df.parse(t);
			    		     long time = date.getTime();
			    		     Timestamp ts = new Timestamp(time);
			    			pstmt.setTimestamp(2, ts);
			    			pstmt.executeUpdate();
			    			System.out.println("데이터삽입성공");
			    		}catch (ParseException e1) {
			    			// TODO Auto-generated catch block
			    			e1.printStackTrace();
			    		}catch(SQLException e1) {
			    			System.out.println("데이터삽입오류 : "+e1.getMessage());
			    		}
			    		JOptionPane.showMessageDialog(null, "게임종료\n"+t);
			    		timer.stop();
			    		
			    		return 1;
			    	}
			   }
			   number++;
	    	}
		}
		return 0;
	}
	//타이머 구현
	public void setstarttime() {//대전할 때 시작 시간 입력
		StartTime=Calendar.getInstance();
	}
	public String playTime() {
		String t="";
		Calendar endTime=Calendar.getInstance();
		long difference=(endTime.getTimeInMillis()-StartTime.getTimeInMillis())/1000;
		t=difference/3600+":";
		difference%=3600;
		t+=difference/60+":";
		difference%=60;
		t+=difference;
		return t;
	}
	public void compare() {
		MariaDB mdb=new MariaDB();
		Connection conn=null;
		conn=mdb.connectDB();
		Statement st = null;
		ResultSet rs = null;
		String ti="";
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT times FROM ranking");
			while(rs.next()) {
				Timestamp time  = rs.getTimestamp(1);
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HH:mm:ss");
				System.out.println("time="+time);
				System.out.println("formatter="+formatter);
				ti=formatter.format(time);
			}
			System.out.println("ti="+ti);
			
		}catch(SQLException e1) {
			System.out.println("데이터삽입오류 : "+e1.getMessage());
		}
	}
	public void Time() {//타이머
		timer=new Timer(1000,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timelabel.setText(playTime());
			}
		});
		timer.start();		
	}
}
