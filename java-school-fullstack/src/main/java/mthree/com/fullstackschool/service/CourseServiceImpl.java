package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseServiceInterface {

    //YOUR CODE STARTS HERE
    CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    //YOUR CODE ENDS HERE

    public List<Course> getAllCourses() {
        //YOUR CODE STARTS HERE

        return courseDao.getAllCourses();

        //YOUR CODE ENDS HERE
    }

    public Course getCourseById(int id) {
        //YOUR CODE STARTS HERE
        Course course = new Course();
        try{
            course = courseDao.findCourseById(id);
        }catch (DataAccessException ex){
            // If DataAccessException is thrown,
            // set CourseName and CourseDesc to "Course Not Found"
            course.setCourseName("Course Not Found");
            course.setCourseDesc("Course Not Found");
        }
        return course;
        //YOUR CODE ENDS HERE
    }

    public Course addNewCourse(Course course) {
        //YOUR CODE STARTS HERE

        //If CourseName or CourseDesc is blank then
        // set CourseName = "Name blank, course NOT added" or
        // set CourseDesc = "Description blank, course NOT added"
        boolean empty = false;

        if(course.getCourseName().isEmpty() || course.getCourseName() == null){
            course.setCourseName("Name blank, course NOT added");
            empty = true;
        }
        if (course.getCourseDesc().isEmpty() || course.getCourseDesc() == null) {
            course.setCourseDesc("Description blank, course NOT added");
            empty = true;
        }

        if(!empty){
            course =  courseDao.createNewCourse(course);
        }


        return course;
        //YOUR CODE ENDS HERE
    }

    public Course updateCourseData(int id, Course course) {
        //YOUR CODE STARTS HERE

        // If id is not equal to object id then
        // set CourseName and CourseDesc = "IDs do not match, course not updated"

        if(id != course.getCourseId()){
            course.setCourseName("IDs do not match, course not updated");
            course.setCourseDesc("IDs do not match, course not updated");
        }else{
            courseDao.updateCourse(course);
        }

        return course;

        //YOUR CODE ENDS HERE
    }

    public void deleteCourseById(int id) {
        //YOUR CODE STARTS HERE

        courseDao.deleteCourse(id);

        // After deletion, print a line to the server console: "Course ID: + id + " deleted"
        System.out.println("Course ID: "+id+" deleted");

        //YOUR CODE ENDS HERE
    }
}
