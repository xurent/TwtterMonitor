package com.xj.Server.utils;

import java.sql.Connection;
import java.sql.SQLException;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.xj.Server.exception.LoginSQLException;
/**
 * ������
 * @author �����
 *
 */
public class jdbcUtils {
	//���ݿ����ӳأ�C3P0
		public static javax.sql.DataSource dataSource=null;
		public static final String DRIVER_NAME="com.mysql.jdbc.Driver";
		public static final String USERNAME="root";
		public static final String PASSWORD="resptfd284";
		public static final String URL="jdbc:mysql://114.112.228.80:3584/fzinfosystem?useUnicode=true&characterEncoding=UTF-8";
		
		static {//��̬����ֻ�ᱻִ��һ��
			try{
			//	dataSource= new ComboPooledDataSource("mysql");
				ComboPooledDataSource pool= new ComboPooledDataSource();
				pool.setDriverClass(DRIVER_NAME);
				pool.setUser(USERNAME);
				pool.setPassword(PASSWORD);
				pool.setJdbcUrl(URL);
				pool.setMaxPoolSize(30);
				pool.setMinPoolSize(5);
				pool.setAcquireIncrement(3);//�����ݿ��е��������������ʱ�������ݿ��������������
				pool.setCheckoutTimeout(10*1000);//�����ӳ�����ʱ�ͻ��˵���getConnection()��ȴ���ȡ�����ӵ�ʱ�䣬��ʱ���׳� SQLException
				pool.setAcquireRetryAttempts(10); //���ӳ��ڻ��������ʧ��ʱ���ԵĴ��������С�ڵ���0����������ֱ�����ӻ�óɹ���default : 30
				dataSource=pool;
			}catch(Exception e){
				System.out.println("�������ӳؼ���ʧ��");	
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
		 * ��ȡ�����ݿ�mysql���������Ӷ���conn
		 * @return
		 */
		
		/**
		 * ͨ�õĹر����ݿ����Ӷ���
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
