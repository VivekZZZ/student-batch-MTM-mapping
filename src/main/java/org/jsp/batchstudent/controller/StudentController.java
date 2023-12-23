package org.jsp.batchstudent.controller;

import java.util.List;

import org.jsp.batchstudent.dto.ResponseStructure;
import org.jsp.batchstudent.dto.Student;
import org.jsp.batchstudent.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@CrossOrigin
public class StudentController {
	@Autowired
	private StudentService studentService;

	@PostMapping("/{batchId}")
	public ResponseEntity<ResponseStructure<Student>> addStudent(@RequestBody Student student,
			@PathVariable int batchId) {
		return studentService.addStudent(student, batchId);
	}

	@PutMapping("/")
	public ResponseEntity<ResponseStructure<Student>> updateStudent(@RequestBody Student student) {
		return studentService.updateStudent(student);
	}

	@PutMapping("/{studentId}/batch/{batchId}")
	public ResponseEntity<ResponseStructure<Student>> addStudentToBatch(@PathVariable int studentId,
			@PathVariable int batchId) {
		return studentService.addStudentToBatch(studentId, batchId);
	}

	@GetMapping("/find-by-batchCode/{batchCode}")
	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsByBatchCode(@PathVariable String batchCode) {
		return studentService.findStudentsByBatchCode(batchCode);
	}

	@GetMapping("/find-by-batchId/{batchId}")
	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsByBatchId(@PathVariable int batchId) {
		return studentService.findStudentsByBatchId(batchId);
	}

	@GetMapping("/find-by-subject/{subject}")
	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsBySubject(@PathVariable String subject) {
		return studentService.findStudentsBySubject(subject);
	}

	@GetMapping("/find-by-trainer-name/{trainer}")
	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsByTrainerName(@PathVariable String trainer) {
		return studentService.findStudentsByTrainerName(trainer);
	}

	@DeleteMapping("/delete-by-stdId/{stdId}")
	public ResponseEntity<ResponseStructure<String>> deleteByStudentId(@PathVariable int stdId) {
		return studentService.deleteStudentById(stdId);
	}
}
