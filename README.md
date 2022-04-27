## This project is to test BATCH insert/update with spring-boot-mybatis

#### Multi-Row Insert
```
    <sql id="insert_columns">
    	INSERT INTO orders(id, customer_id, product, buy_sell, amount, price, status, open_timestamp)
    </sql>
	<sql id="insert_item_values">
    	(#{item.id}, #{item.customerId}, #{item.product}, #{item.buySell}, #{item.amount}, #{item.price}, #{item.status}, #{item.openTimestamp})
    </sql>
    <insert id="newOrders">
	    <include refid="insert_columns" />
	    values
		<foreach item="item" index="index" collection="list" separator=",">
			<include refid="insert_item_values"></include>
		</foreach>
	</insert>
```
It builds a single PreparedStatement with many placeholders ?.   
Its performance gets worse when there are many columns and/or items in the list


#### Single-Row Insert - ExecutorType.Simple AutoCommit=TRUE
```
   <sql id="insert_columns">
    	INSERT INTO orders(id, customer_id, product, buy_sell, amount, price, status, open_timestamp)
    </sql>
    <sql id="insert_values">
    	values(#{id}, #{customerId}, #{product}, #{buySell}, #{amount}, #{price}, #{status}, #{openTimestamp})
    </sql>
   <insert id="newOrder">
		<include refid="insert_columns" />
		<include refid="insert_values" />
	</insert>

```
Use Mapper class directly.   
Create one PreparedStatement for each new now, no PreparedStatements will be reused.  

#### Single-Row Insert - BATCH mode AutoCommit=FALSE

It builds a simple PreparedStatement (with one placeholder in you case) and reuse it.
But currently the only way I found is to explicitly open new session in BATCH mode via ```sqlSessionFactory```
Do flushStatements after some number of rows are executed.  


```java
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
```

Note
---

1. spring-boot-mybatis offers a way to set executorType to batch globally.
It also turns the autoCommit to false and has to use it with @Transactional for commit.

```yaml
mybatis:
  configuration:
    default-executor-type: BATCH
```
2. MyBatis tests are transactional and rollback at the end of each test by default.
