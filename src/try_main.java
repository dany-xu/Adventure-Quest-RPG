import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class try_main {
	public static void main(String[] args) {
		try {
			// 1.加载驱动
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2.获取连接
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//127.0.0.1:1521/orcl", "scott",
					"tiger");
			System.out.println(connection.getMetaData());
			// 3.创建Statement对象（可以执行sql的对象）
			PreparedStatement preparedStatement = connection.prepareStatement("select * from dept");
			// 4.获取结果
			ResultSet resultSet = preparedStatement.executeQuery();
			// 5.对数据进行处理
			while (resultSet.next()) {
				Integer id = resultSet.getInt("DEPTNO");
				String dname = resultSet.getString("DNAME");
				String location = resultSet.getString("LOC");
				System.out.println(id + " " + dname + " " + location);
			}
			// 6.关闭连接 先调用的最后关闭 关闭前判断是否存在
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
