import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDB {
	private static final String DB_DRIVER_CLASS="org.mariadb.jdbc.Driver";
	private static final String DB_URL="jdbc:mariadb://127.0.0.1:3306/puzzle";
	private static final String DB_USERNAME="root";
	private static final String DB_PASSWORD="1234";
	private static Connection conn;
	static Connection connectDB() {
		try {
			Class.forName(DB_DRIVER_CLASS);
			conn=DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
			System.out.println("���� ����");
//			String sql = "SELECT * FROM information";
//			Statement st = null;
//			ResultSet rs = null;
//			st = conn.createStatement();
//			rs = st.executeQuery(sql);
//            while(rs.next()) {
//                
//                // ���ڵ��� �÷��� 0�� �ƴ� 1���� ����.
//                String userId  = rs.getString(1);
//                String userPw  = rs.getString(2);
//                
//                System.out.println(userId + " " + userPw);
//                
//                // ���ڵ��� �÷������ε� ������ �� �ֽ��ϴ�.
////                String userId2  = rs.getString("USER_ID");
////                String userPw2  = rs.getString("USER_PW");
////                String userTel2 = rs.getString("USER_TEL");
//                
////               System.out.println(userId2 + " " + userPw2 + " " + userTel2);
//            }
		}catch(ClassNotFoundException e) {
			System.out.println("����̺� �ε� ����");
		}catch(SQLException e) {
			System.out.println("DB ���� ����");
		}
		return conn;
	}
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		MariaDB test=new MariaDB();
//		test.connectDB();
//	}

}

