package mthree.com.fullstackschool.service;

import mthree.com.fullstackschool.dao.TeacherDao;
import mthree.com.fullstackschool.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherServiceInterface {

    //YOUR CODE STARTS HERE
    private TeacherDao teacherDao;

    @Autowired
    public TeacherServiceImpl(TeacherDao teacherDao){
        this.teacherDao = teacherDao;
    }

    //YOUR CODE ENDS HERE

    public List<Teacher> getAllTeachers() {
        //YOUR CODE STARTS HERE

        return teacherDao.getAllTeachers();

        //YOUR CODE ENDS HERE
    }

    public Teacher getTeacherById(int id) {
        //YOUR CODE STARTS HERE

        try{
            return teacherDao.findTeacherById(id);
        }catch (DataAccessException ex){
            // If DataAccessException is thrown,
            // set FirstName and LastName to "Student Not Found"

            Teacher teacher = new Teacher();
            teacher.setTeacherFName("Student Not Found");
            teacher.setTeacherLName("Student Not Found");

            return teacher;
        }

        //YOUR CODE ENDS HERE
    }

    public Teacher addNewTeacher(Teacher teacher) {
        //YOUR CODE STARTS HERE

        // If FirstName or LastName is blank,
        // set FirstName = "First Name blank, teacher NOT added"
        // or set LastName = "Last Name blank, teacher NOT added"

        boolean empty = false;

        if(teacher.getTeacherFName().isEmpty() || teacher.getTeacherFName() == null){
            teacher.setTeacherFName("First Name blank, teacher NOT added" );
            empty = true;
        }

        if(teacher.getTeacherLName().isEmpty() || teacher.getTeacherLName() == null){
            teacher.setTeacherLName("Last Name blank, teacher NOT added");
            empty = true;
        }

        if(!empty){
            teacher = teacherDao.createNewTeacher(teacher);
        }
        return teacher;

        //YOUR CODE ENDS HERE
    }

    public Teacher updateTeacherData(int id, Teacher teacher) {
        //YOUR CODE STARTS HERE

        // If id is not equal to object id then set TeacherFName
        // and TeacherLName = "IDs do not match, teacher not updated"

        if(id != teacher.getTeacherId()){
            teacher.setTeacherFName("IDs do not match, teacher not updated");
            teacher.setTeacherLName("IDs do not match, teacher not updated");
        }else{
            teacherDao.updateTeacher(teacher);
        }

        return teacher;

        //YOUR CODE ENDS HERE
    }

    public void deleteTeacherById(int id) {
        //YOUR CODE STARTS HERE

        teacherDao.deleteTeacher(id);

        //YOUR CODE ENDS HERE
    }
}
