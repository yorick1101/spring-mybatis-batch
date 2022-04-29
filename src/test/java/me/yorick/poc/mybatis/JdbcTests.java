package me.yorick.poc.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StopWatch;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // 使用真实的数据库
//@Rollback(value = false)    //default is true, rollback after test
public class JdbcTests {
	
   @Autowired
   private JdbcTemplate jdbcTemplate;
	
   int count = 20000;
   
   @Test
   public void testMultiConcateStatement() {
	   StringBuilder sql = new StringBuilder();
	   IntStream.range(1,  count).forEach(i->
	   	sql.append("insert into trades.orders values(UUID(), '1','p1', 'BUY', 0, 0,'OPEN', now(), null);")
	   );
	   final StopWatch watch = new StopWatch();
	   watch.start();
	   jdbcTemplate.execute(sql.toString());
	   watch.stop();
	   System.out.println("testMultiConcatedStatement:"+watch.getLastTaskTimeMillis());
   }
   
   @Test
   public void testSingleStatement() {
	   StringBuilder sql = new StringBuilder();
	   sql.append("insert into trades.orders values(UUID(), '1','p1', 'BUY', 0, 0,'OPEN', now(), null)");
	   IntStream.range(1,  count).forEach(i->
	   	sql.append(",(UUID(), '1','p1', 'BUY', 0, 0,'OPEN', now(), null)")
	   );
	   final StopWatch watch = new StopWatch();
	   watch.start();
	   jdbcTemplate.execute(sql.toString());
	   watch.stop();
	   System.out.println("testSingleStatement:"+watch.getLastTaskTimeMillis());
   }
   
   @Test
   public void testMultiStatement() {
	   List<String> sqls = new ArrayList<>();
	   IntStream.range(1,  count).forEach(i->
	   	 sqls.add("insert into trades.orders values(UUID(), '1','p1', 'BUY', 0, 0,'OPEN', now(), null);")
	   );
	   final StopWatch watch = new StopWatch();
	   watch.start();
	   for(String sql: sqls)
		   jdbcTemplate.execute(sql);
	   watch.stop();
	   System.out.println("testMultiStatement:"+watch.getLastTaskTimeMillis());
   }
   
   

   
}
