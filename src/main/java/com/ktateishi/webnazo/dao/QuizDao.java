package com.ktateishi.webnazo.dao;

import com.ktateishi.webnazo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class QuizDao {

    private final JdbcTemplate jdbcTemplate;

    public User fetchUser(String name) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" * ");
        sql.append("FROM ");
        sql.append("( ");
        sql.append("  SELECT ");
        sql.append("    * ");
        sql.append("    ,  RANK() OVER(PARTITION BY name ORDER BY id ASC) AS rnk ");
        sql.append("  FROM ");
        sql.append("    user ");
        sql.append(") tmp ");
        sql.append("WHERE ");
        sql.append("  rnk = 1 ");
        sql.append("  AND name = ? ");
        List<User> users = jdbcTemplate.queryForList(sql.toString())
                .stream()
                .map( e -> new User((String) e.get("name"), (String) e.get("regist_date"), (int) e.get("rnk")))
                .collect(Collectors.toList());
        return users.size() > 0 ? users.get(0) : null;
    }

    public List<User> fetchUserList() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" * ");
        sql.append("FROM ");
        sql.append("( ");
        sql.append("  SELECT ");
        sql.append("    * ");
        sql.append("    ,  RANK() OVER(ORDER BY id ASC) AS rnk ");
        sql.append("  FROM ");
        sql.append("  ( ");
        sql.append("    SELECT ");
        sql.append("      * ");
        sql.append("      , RANK() OVER(PARTITION BY name ORDER BY id ASC) AS same_rnk ");
        sql.append("    FROM ");
        sql.append("      user ");
        sql.append("  ) ");
        sql.append("  WHERE ");
        sql.append("    same_rnk = 1 ");
        sql.append(") ");
        sql.append("ORDER BY ");
        sql.append("  rnk ASC ");
        return jdbcTemplate.queryForList(sql.toString())
                .stream()
                .map( e -> new User((String) e.get("name"), (String) e.get("regist_date"), (int) e.get("rnk")))
                .collect(Collectors.toList());
    }

    public void insertUser(String name) {
        jdbcTemplate.update("INSERT INTO user (name, regist_date) values (?, strftime('%Y-%m-%d %H:%M:%S', 'now', 'localtime'))", name);
    }

    public void deleteUser() {
        jdbcTemplate.update("DELETE FROM user");
    }
}
