package com.client.service;

import com.client.entity.Student;
import com.client.exception.StudentNotFoundException;
import com.client.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StudentService {

    public static final Logger logger = Logger.getLogger(StudentService.class.getName());

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents(){

        try {
            List<Student> studentList = studentRepository.findAll();
            logger.info("Successfully retrieved -> "+ studentList.size()+" students info");
            return studentList;
        }
        catch(Exception e){
            logger.log(Level.SEVERE, "Exception occurred while fetching all students from the RDS", e);
            throw e;
        }
    }

    public Student saveStudent(Student student){

        try {
            student.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Student savedStudent = studentRepository.save(student);
            return savedStudent;
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "Exception occurred while saving student from the RDS");
            throw e;
        }
    }

    public Student getStudentById(Long id){

        try {
            return studentRepository.findById(id)
                    .orElseThrow(() -> new StudentNotFoundException(id));
        }
        catch (Exception e){
            logger.log(Level.SEVERE, "Exception occurred while getting student using ID");
            throw e;
        }
    }

    public Student updateStudent(Student newStudent, Long id){

        return studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setLastName(newStudent.getLastName());
                    student.setAge(newStudent.getAge());
                    return studentRepository.save(student);
                }).orElseThrow(() -> new StudentNotFoundException(id));
    }

    public String deleteStudent(Long id){

        if (!studentRepository.existsById(id)){
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
        return "Student with ID = " + id + " has been deleted successfully";
    }


}
