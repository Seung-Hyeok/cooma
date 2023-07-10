package coo.admin.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShStatisticsRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ShReservationDTO> getSalesData() {
    	
    
        String query = "SELECT\r\n"
        		+ "CASE\r\n"
        		+ "WHEN MONTH(payD) = 1 THEN '1월'\r\n"
        		+ "WHEN MONTH(payD) = 2 THEN '2월'\r\n"
        		+ "WHEN MONTH(payD) = 3 THEN '3월'\r\n"
        		+ "WHEN MONTH(payD) = 4 THEN '4월'\r\n"
        		+ "WHEN MONTH(payD) = 5 THEN '5월'\r\n"
        		+ "WHEN MONTH(payD) = 6 THEN '6월'\r\n"
        		+ "WHEN MONTH(payD) = 7 THEN '7월'\r\n"
        		+ "WHEN MONTH(payD) = 8 THEN '8월'\r\n"
        		+ "WHEN MONTH(payD) = 9 THEN '9월'\r\n"
        		+ "WHEN MONTH(payD) = 10 THEN '10월'\r\n"
        		+ "WHEN MONTH(payD) = 11 THEN '11월'\r\n"
        		+ "WHEN MONTH(payD) = 12 THEN '12월'\r\n"
        		+ "END AS month,\r\n"
        		+ "SUM(totFee) AS amount\r\n"
        		+ "FROM reser\r\n"
        		+ "GROUP BY MONTH(payD)";
        
        return jdbcTemplate.query(query, (rs, rowNum) -> {
        	
            String month = rs.getString("month");
            int amount = rs.getInt("amount");
            return new ShReservationDTO(month, amount);
        });
    }
}
