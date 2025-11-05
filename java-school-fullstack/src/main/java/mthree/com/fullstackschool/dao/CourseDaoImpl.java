package mthree.com.fullstackschool.dao;

import mthree.com.fullstackschool.dao.mappers.CourseMapper;
import mthree.com.fullstackschool.model.Course;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Course createNewCourse(Course course) {
        //YOUR CODE STARTS HERE
        final String sql = "INSERT INTO course(courseCode, courseDesc, teacherId) VALUES (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        try{
            jdbcTemplate.update((Connection conn)->{
                PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1,course.getCourseName());
                stmt.setString(2,course.getCourseDesc());
                stmt.setInt(3,course.getTeacherId());
                return stmt;
            },keyHolder);

            Number key = keyHolder.getKey();
            if(key != null){
                course.setCourseId(key.intValue());
            }

            return course;
        }catch (DataAccessException ex){
            return null;
        }

        //YOUR CODE ENDS HERE
    }

    @Override
    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE
        final String SELECT_ALL_COURSES = "SELECT * FROM course";

        return jdbcTemplate.query(SELECT_ALL_COURSES, new CourseMapper());
        //YOUR CODE ENDS HERE
    }

    @Override
    public Course findCourseById(int id) throws DataAccessException{
        //YOUR CODE STARTS HERE
        final String SELECT_COURSE_BY_ID = "SELECT * FROM course WHERE cid = ?";
        return jdbcTemplate.queryForObject(SELECT_COURSE_BY_ID,new CourseMapper(),id);

        //YOUR CODE ENDS HERE
    }

    @Override
    public void updateCourse(Course course) {
        //YOUR CODE STARTS HERE
        final String UPDATE_COURSE = "UPDATE course SET courseCode = ?, courseDesc = ?, teacherId = ?";

        jdbcTemplate.update(UPDATE_COURSE,course.getCourseName(),course.getCourseDesc(),course.getTeacherId());
        //YOUR CODE ENDS HERE
    }

    @Override
    @Transactional
    public void deleteCourse(int id) {
        //YOUR CODE STARTS HERE

        // Delete the records in course_student
        deleteAllStudentsFromCourse(id);

        // Delete the course in course
        final  String DELETE_COURSE_BY_ID = "DELETE from course WHERE cid = ?";
        jdbcTemplate.update(DELETE_COURSE_BY_ID,id);
        //YOUR CODE ENDS HERE
    }

    @Override
    @Transactional
    public void deleteAllStudentsFromCourse(int courseId) {
        //YOUR CODE STARTS HERE

        final String DELETE_STUDENTS_FROM_COURSE = "DELETE " +
                "FROM course_student cs " +
                "WHERE course_id = ?";

        jdbcTemplate.update(DELETE_STUDENTS_FROM_COURSE,courseId);
        //YOUR CODE ENDS HERE
    }
}
