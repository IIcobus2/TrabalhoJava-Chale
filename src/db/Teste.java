package db;

import java.sql.Connection;

public class Teste {
	public static void main(String[] args) {
		Connection con = ConnectionFactory.getConnection();
		if (con != null) {
			System.out.println("OK");
			ConnectionFactory.close(con);
		}
	}

}
