import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
public class Ranking extends JFrame{
	public Ranking() {
		super("Ranking");
		setLayout(new BorderLayout());
		JLabel l=new JLabel("🥇Ranking🥇",JLabel.CENTER);
		l.setFont(new Font("Serif",Font.BOLD,20));
		l.setFont(l.getFont().deriveFont(30.0f));
		String [][]rank;
		String []title= {"랭킹","id","time"};
		int i=0,c=0;
		MariaDB mdb=new MariaDB();
		Connection conn=null;
		conn=mdb.connectDB();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT COUNT(*) FROM information");
			while(rs.next()) {
				c  = rs.getInt(1);
			}
			//System.out.println("c="+c);
			rank=new String[c][5];
			///////////////////////////////////고쳐야하오오오오오오오오오오오오오!!!!!!!!!!!!!!!!!!!!!!!!
			rs = st.executeQuery("SELECT rank() over(order by win DESC) AS rank,id,times FROM Ranking;");//시간 초 빠른 순서대로 고쳐야함
			while(rs.next()) {
				int ra  = rs.getInt(1);
				String id  = rs.getString(2);
				Timestamp time  = rs.getTimestamp(3);
				java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("HH:mm:ss");
				rank[i][0]=Integer.toString(ra);
				rank[i][1]=id;
				rank[i][2]=formatter.format(time);
				i++;
			}
			DefaultTableModel model = new DefaultTableModel(rank,title);
			JTable table = new JTable(model);
			JScrollPane sc = new JScrollPane(table);
			add("North",l);
			add("Center",sc);
			DefaultTableModel m =(DefaultTableModel)table.getModel();
//			addWindowListener(new WindowAdapter() {
//				public void windowClosing(WindowEvent e) {
//					System.exit(0);
//				}
//			});
			setBounds(350,150,500,500);
			setVisible(true);
		}catch(SQLException e1) {
			System.out.println("데이터삽입오류 : "+e1.getMessage());
		}
	}
}
