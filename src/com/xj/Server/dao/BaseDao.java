package com.xj.Server.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.log4j.Logger;

import com.xj.Server.exception.LoginSQLException;
import com.xj.Server.utils.jdbcUtils;
public class BaseDao<T> {
private Class<T> clazz;
	
	private Logger logger=Logger.getLogger(this.getClass());//��־
	
	@SuppressWarnings("unchecked")
	public  BaseDao(){
		//��BaseDao�Ĺ��췽����ʼ��Clazz����
		Type superType=this.getClass().getGenericSuperclass();//�õ������ŷ����������
		if(superType instanceof ParameterizedType){
			ParameterizedType pType=(ParameterizedType) superType;
			Type[] tarry=pType.getActualTypeArguments();//User.Class
			if(tarry[0] instanceof Class){
			clazz=(Class<T>) tarry[0];
			System.out.println(tarry);
			}
		}
	}
	QueryRunner queryRunner =new QueryRunner();
	/**
	 * ��ѯ���ݱ�ȡ��sql���Ľ�����ĵ�һ�����ݣ���װ��һ����Ķ��󷵻أ���֧������
	 * @param sql
	 * @param args
	 * @return
	 */
	
	/**
	 * null��λ��Ӧ�ô���BaseDao<T>�����T�������õ�ʱ������͵�Class
	 * @param sql
	 * @param args
	 * @return
	 * @throws LoginSQLException 
	 */
		public T get(String sql,Object... args) throws LoginSQLException {
			
			Connection conn= null;
			T entity=null;
			try {
					conn=jdbcUtils.getConnection();
					entity= queryRunner.query(conn, sql,new BeanHandler<T>(clazz), args);
			} catch (Exception e) {
				System.out.println("���ݿ��쳣");
				logger.info("���ݿ�get�쳣:"+e.getMessage()+"\n");
				throw new LoginSQLException(e);
			}finally {
				jdbcUtils.CloseConn(conn);
			}
			return entity;
			
		}
		
		public T get(Connection conn,String sql,Object... args){
			T entity=null;
			try {
				entity= queryRunner.query(conn, sql,new BeanHandler<T>(clazz), args);
			} catch (Exception e) {
				logger.info("���ݿ� get�쳣:"+e.getMessage());
			}			
			return entity;
			
		}
		
		/**
		 * ��ȡ������¼��ͨ�÷���
		 * @return
		 */
		public List<T> getList(String sql,Object... args){
			
			Connection conn= null;
			List<T>list=null;
			try {
					conn=jdbcUtils.getConnection();
					list= queryRunner.query(conn, sql,new BeanListHandler<>(clazz), args);
			} catch (Exception e) {
				System.out.println("���ݿ��쳣:"+e.getMessage()+"\n");
				logger.info("���ݿ��쳣getList:"+e.getMessage());
			}finally {
				jdbcUtils.CloseConn(conn);
			}
			return list;
		}
	
		/**
		 * ͨ��ʵ�� insert��update��delete�ĸ��·���
		 * @param sql
		 * @param args
		 * @return
		 */
	public int update(String sql,Object... args){
		
		Connection conn= null;
		int rows=0;
		try {
				conn=jdbcUtils.getConnection();
				rows= queryRunner.update(conn,sql,args);
		} catch (Exception e) {
			logger.info("���ݿ�update�쳣"+e.getMessage()+"\n");
			e.printStackTrace();
		}finally {
			jdbcUtils.CloseConn(conn);
		}
		return rows;
	}
	
	/**
	 * ͨ�õķ���sql�����ֻ��һ����ֵ�����͵Ĳ�ѯ,����count(id)
	 * @param sql
	 * @param args
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getValue(String sql,Object... args){
		Connection conn= null;
		Object obj=null;
		try {
				conn=jdbcUtils.getConnection();
				obj= queryRunner.query(conn,sql, new ScalarHandler(),args);
		} catch (Exception e) {
			logger.info("���ݿ�Object getValue�쳣"+e.getMessage()+"\n");
			e.printStackTrace();
		}finally {
			jdbcUtils.CloseConn(conn);
		}
		return obj;
	
	}

	

}
