package com.getir.reading.repository;

import com.getir.reading.model.OrderLine;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class OrderCustomRepository extends BaseRepository {
    public void updateTotalAmount(long orderNumber, double totalAmount) {
        Query query = entityManager.createQuery("update Orders o set o.totalAmount = :totalAmount where o.orderNumber = :orderNumber ");
        query.setParameter("orderNumber", orderNumber);
        query.setParameter("totalAmount", totalAmount);
        query.executeUpdate();
    }

    public List<OrderLine> getOrderLines(long orderNumber) {
        Query query = entityManager.createQuery("from OrderLine o where o.orderNumber = :orderNumber");
        query.setParameter("orderNumber", orderNumber);
        return query.getResultList();
    }

    public List<OrderLine> list(String startDate, String endDate) {
        Query query = entityManager.createQuery("from OrderLine o where to_char(o.createdDate, 'YYYY-MM-DD') >= :startDate and to_char(o.createdDate, 'YYYY-MM-DD') <= :endDate");
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<OrderLine> getOrderLines(String email, Integer page, Integer pageSize) {
        Query query = entityManager.createQuery("from OrderLine o where o.email = :email");
        query.setParameter("email", email);
        query.setFirstResult(calculateOffset(page, pageSize));
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    private int calculateOffset(int page, int limit) {
        return ((limit * page) - limit);
    }
}
