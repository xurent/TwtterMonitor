package com.xj.Server.utils;

import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xj.Server.exception.LoginSQLException;
/**
 * 工具类
 * @author 何旭杰
 *
 */
public class jdbcUtils {
	//数据库连接池，C3P0
		public static javax.sql.DataSource dataSource=null;
		public static final String DRIVER_NAME="com.mysql.jdbc.Driver";
		public static final String USERNAME="root";
		public static final String PASSWORD="resptfd284";
		public static final String URL="jdbc:mysql://114.112.228.80:3584/fzinfosystem?useUnicode=true&characterEncoding=UTF-8";
		
		static {//静态代码只会被执行一次
			try{
			//	dataSource= new ComboPooledDataSource("mysql");
				ComboPooledDataSource pool= new ComboPooledDataSource();
				pool.setDriverClass(DRIVER_NAME);
				pool.setUser(USERNAME);
				pool.setPassword(PASSWORD);
				pool.setJdbcUrl(URL);
				pool.setMaxPoolSize(30);
				pool.setMinPoolSize(5);
				pool.setAcquireIncrement(3);//若数据库中的连接数量不足的时候，向数据库申请的连接数量
				pool.setCheckoutTimeout(10*1000);//当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出 SQLException
				pool.setAcquireRetryAttempts(10); //连接池在获得新连接失败时重试的次数，如果小于等于0则无限重试直至连接获得成功。default : 30
				dataSource=pool;
			}catch(Exception e){
				System.out.println("数据连接池加载失败");	
				try {
					throw new LoginSQLException(e);
				} catch (LoginSQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		
		public static Connection getConnection()throws LoginSQLException, SQLException{
			return  dataSource.getConnection();
			
		}
		/**
		 * 获取到数据库mysql的数据连接对象conn
		 * @return
		 */
		
		/**
		 * 通用的关闭数据库连接对象
		 */
		public static void CloseConn(Connection conn){
			if(conn!=null){
			try {
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			}
		}
		
		
		public static void rollbackTransation(Connection conn){
			if(conn!=null){
				try {
					conn.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
}
