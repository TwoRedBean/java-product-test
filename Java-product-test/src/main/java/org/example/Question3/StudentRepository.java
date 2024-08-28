package org.example.Question3;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate template;

    @PostConstruct
    public void createTable() {
        template.execute("CREATE TABLE student(id bigint auto_increment primary key, name VARCHAR(50), gender VARCHAR(1))");
    }

    public void createStudent(String name, String gender) {
    template.update("INSERT INTO student(id, name, gender) VALUES (?,?,?)", null, name, gender);
    }

    public List<Student> findStudentByName(String nameStartsWith) {
        String sql = "SELECT name, gender FROM student WHERE name LIKE ?";
        return template.query(sql, new Object[]{nameStartsWith + "%"}, (rs, rowNum) ->
                new Student(rs.getString("name"), rs.getString("gender"))
        );
    }
}
