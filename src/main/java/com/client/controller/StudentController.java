package com.client.controller;

import com.client.entity.Student;
import com.client.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/student")
public class StudentController {

    private static final Logger logger = Logger.getLogger(StudentController.class.getName());

    @Autowired
    private StudentService studentService;

    @GetMapping("/getAll")
    private List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping("/save")
    private Student saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @GetMapping("/getById/{id}")
    private Student getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    private Student updateStudent(@RequestBody Student newStudent, @PathVariable Long id){
        return studentService.updateStudent(newStudent, id);
    }



}
