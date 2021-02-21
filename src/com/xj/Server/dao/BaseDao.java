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
	
	private Logger logger=Logger.getLogger(this.getClass());//日志
	
	@SuppressWarnings("unchecked")
	public  BaseDao(){
		//用BaseDao的构造方法初始化Clazz属性
		Type superType=this.getClass().getGenericSuperclass();//拿到调用着发父类的属性
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
	 * 查询数据表，取出sql语句的结果集的第一条数据，封装成一个类的对象返回，不支持事物
	 * @param sql
	 * @param args
	 * @return
	 */
	
	/**
	 * null的位置应该传入BaseDao<T>里面的T是真正用的时候的类型的Class
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
				System.out.println("数据库异常");
				logger.info("数据库get异常:"+e.getMessage()+"\n");
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
				logger.info("数据库 get异常:"+e.getMessage());
			}			
			return entity;
			
		}
		
		/**
		 * 获取多条记录的通用方法
		 * @return
		 */
		public List<T> getList(String sql,Object... args){
			
			Connection conn= null;
			List<T>list=null;
			try {
					conn=jdbcUtils.getConnection();
					list= queryRunner.query(conn, sql,new BeanListHandler<>(clazz), args);
			} catch (Exception e) {
				System.out.println("数据库异常:"+e.getMessage()+"\n");
				logger.info("数据库异常getList:"+e.getMessage());
			}finally {
				jdbcUtils.CloseConn(conn);
			}
			return list;
		}
	
		/**
		 * 通用实现 insert，update，delete的更新方法
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
			logger.info("数据库update异常"+e.getMessage()+"\n");
			e.printStackTrace();
		}finally {
			jdbcUtils.CloseConn(conn);
		}
		return rows;
	}
	
	/**
	 * 通用的返回sql语句结果只有一个数值的类型的查询,例如count(id)
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
			logger.info("数据库Object getValue异常"+e.getMessage()+"\n");
			e.printStackTrace();
		}finally {
			jdbcUtils.CloseConn(conn);
		}
		return obj;
	
	}

	

}
