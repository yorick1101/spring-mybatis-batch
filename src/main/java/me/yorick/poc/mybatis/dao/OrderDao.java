package me.yorick.poc.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.yorick.poc.mybatis.mapper.OrderMapper;
import me.yorick.poc.mybatis.model.Order;

@Service
public class OrderDao {
	
	
	private SqlSessionFactory sqlSessionFactory;
	
	@Autowired
	public OrderDao(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	public void batchSaveOrdersEach(List<Order> orders) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
		OrderMapper mapper = session.getMapper(OrderMapper.class);
		
		try {
			for(Order order: orders)
				mapper.newOrder(order);
			session.flushStatements();
			session.commit();
		}catch(Exception e) {
			session.rollback();
		}finally {
			
			session.close();
		}
	}
	
	public void batchSaveOrdersOnce(List<Order> orders) {
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH);
		OrderMapper mapper = session.getMapper(OrderMapper.class);
		
		try {
			mapper.newOrders(orders);
			session.flushStatements();
			session.commit();
		}catch(Exception e) {
			session.rollback();
		}finally {
			
			session.close();
		}
	}
	
	
	
}
