package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.TeacherMapper;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TeacherDaoImpl implements TeacherDao {

    private final JdbcTemplate jdbcTemplate;

    public TeacherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Teacher createNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE

        final String INSERT_TEACHER = "INSERT INTO teacher (tFName, tLName, dept) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try{
            jdbcTemplate.update((Connection conn) -> {
                PreparedStatement stmt = conn.prepareStatement(INSERT_TEACHER,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1,teacher.getTeacherFName());
                stmt.setString(2,teacher.getTeacherLName());
                stmt.setString(3,teacher.getDept());
                return stmt;
            },keyHolder);

            Number key = keyHolder.getKey();
            if(key != null){
                teacher.setTeacherId(key.intValue());
            }

            return teacher;
        }catch (DataAccessException ex){
            return null;
        }

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE
        final String SELECT_ALL_TEACHERS = "SELECT * FROM teacher";

        return jdbcTemplate.query(SELECT_ALL_TEACHERS,new TeacherMapper());

        //YOUR CODE ENDS HERE
    }

    @Override
    public Teacher findTeacherById(int id) throws DataAccessException{
        //YOUR CODE STARTS HERE
        final String SELECT_TEACHER_BY_ID = "SELECT * FROM teacher WHERE tid = ?";
        return jdbcTemplate.queryForObject(SELECT_TEACHER_BY_ID,new TeacherMapper(),id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateTeacher(Teacher t) {
        //YOUR CODE STARTS HERE

        final String UPDATE_TEACHER = "UPDATE teacher SET tFName = ?, tLName = ?, dept = ? WHERE tid = ?";

        jdbcTemplate.update(UPDATE_TEACHER,t.getTeacherFName(),t.getTeacherLName(),t.getDept(),t.getTeacherId());

        //YOUR CODE ENDS HERE
    }

    @Override
    @Transactional
    public void deleteTeacher(int id) {
        //YOUR CODE STARTS HERE
        final String DELETE_TEACHER = "DELETE FROM teacher WHERE tid = ?";

        jdbcTemplate.update(DELETE_TEACHER,id);
        //YOUR CODE ENDS HERE
    }
}
