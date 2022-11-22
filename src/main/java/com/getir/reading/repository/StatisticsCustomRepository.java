package com.getir.reading.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class StatisticsCustomRepository extends BaseRepository {
    public List<Map<String, Object>> getMonthlyReport() {
        Query query = entityManager.createQuery("select new map(to_char(o.createdDate, 'Month') as monthName, count(*) as orderCount, sum(o.quantity) as totalQuantity, sum(o.amount) as totalAmount) from OrderLine o group by to_char(o.createdDate, 'Month')");
        return query.getResultList();
    }
}
