package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.CourseDao;
import mthree.com.fullstackschool.dao.StudentDao;
import mthree.com.fullstackschool.model.Course;
import mthree.com.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE

    StudentDao studentDao;

    @Autowired
    CourseServiceInterface courseService;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao){
        this.studentDao = studentDao;
    }

    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        return studentDao.getAllStudents();

        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE

        try{
            return studentDao.findStudentById(id);
        }catch (DataAccessException ex){
            // If DataAccessException is thrown, set FirstName and LastName to "Student Not Found"
            Student student = new Student();
            student.setStudentFirstName("Student Not Found");
            student.setStudentLastName("Student Not Found");
            return student;
        }

        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE
        // If FirstName or LastName is blank,
        // set FirstName = "First Name blank, student NOT added"
        // or set LastName = "Last Name blank, student NOT added"

        boolean empty = false;

        if(student.getStudentFirstName().isEmpty() || student.getStudentFirstName() == null){
            student.setStudentFirstName("First Name blank, student NOT added");
            empty = true;
        }

        if(student.getStudentLastName().isEmpty() || student.getStudentLastName() == null){
            student.setStudentLastName("Last Name blank, student NOT added");
            empty = true;
        }

        if(!empty){
            student = studentDao.createNewStudent(student);
        }

        return student;

        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE

        // If id is not equal to object id,
        // set FirstName and LastName = "IDs do not match, student not updated"
        if(id != student.getStudentId()){
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
        }else{
            studentDao.updateStudent(student);
        }
        return student;
        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE

        studentDao.deleteStudent(id);


        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        /*
        * If student first name is equal to "Student Not Found"
        *   print a line to server console "Student not found"
        else if course name is equal to "Course Not Found"
        *   print a line to server console "Course not found"
        else
        *   print a line to server console "Student: <STUDENT ID> deleted from course: <COURSE ID>"
        * */

        Student student = getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);
        if(student.getStudentFirstName().equals("Student Not Found")){
            System.out.println("Student not found");
        }else if(course.getCourseName().equals("Course Not Found")){
            System.out.println("Course Not Found");
        }else{
            studentDao.deleteStudentFromCourse(studentId,courseId);
            System.out.println("Student: "+studentId+" deleted from course: "+courseId);
        }

        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE


        //YOUR CODE ENDS HERE
    }
}
