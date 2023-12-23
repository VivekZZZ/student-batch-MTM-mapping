package org.jsp.batchstudent.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jsp.batchstudent.dao.BatchDao;
import org.jsp.batchstudent.dao.StudentDao;
import org.jsp.batchstudent.dto.Batch;
import org.jsp.batchstudent.dto.ResponseStructure;
import org.jsp.batchstudent.dto.Student;
import org.jsp.batchstudent.exception.BatchNotFoundException;
import org.jsp.batchstudent.exception.StudentAlreadyPresentException;
import org.jsp.batchstudent.exception.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	@Autowired
	private StudentDao studentDao;
	@Autowired
	private BatchDao batchDao;

	public ResponseEntity<ResponseStructure<Student>> addStudent(Student student, int batchId) {
		ResponseStructure<Student> structure = new ResponseStructure<>();
		Optional<Batch> recBatch = batchDao.findById(batchId);
		if (recBatch.isPresent()) {

			if (student.getBatches() == null) {
				student.setBatches(new HashSet<Batch>());
			}
			student.getBatches().add(recBatch.get());
			Student dbStudent = studentDao.addStudent(student);
			structure.setData(dbStudent);
			structure.setMessage("Student Added");
			structure.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.CREATED);
		}
		throw new StudentNotFoundException("Invalid Batch Id");
	}

	public ResponseEntity<ResponseStructure<Student>> updateStudent(Student student) {
		ResponseStructure<Student> structure = new ResponseStructure<>();
		Optional<Student> recStudent = studentDao.findById(student.getId());
		if (recStudent.isPresent()) {
			Student dbStudent = recStudent.get();
			dbStudent.setAge(student.getAge());
			dbStudent.setName(student.getName());
			dbStudent.setPercentage(student.getPercentage());
			dbStudent.setPhone(student.getPhone());
			structure.setData(studentDao.addStudent(dbStudent));
			structure.setMessage("Student Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.ACCEPTED);
		}
		throw new StudentNotFoundException("Invalid Student Id");
	}

	public ResponseEntity<ResponseStructure<Student>> addStudentToBatch(Integer studentId, Integer batchId) {
		ResponseStructure<Student> structure = new ResponseStructure<>();
		Optional<Student> recStudent = studentDao.findById(studentId);
		Optional<Batch> recBatch = batchDao.findById(batchId);
		if (recStudent.isPresent() && recBatch.isPresent()) {
			Set<Batch> batches = new HashSet<>();
			Batch dbBatch = recBatch.get();
			Student dbStudent = recStudent.get();
			batches = dbStudent.getBatches();
			batches.add(dbBatch);
			dbStudent.setBatches(batches);
			if(!dbBatch.getStudents().contains(dbStudent)) {
				structure.setData(studentDao.addStudent(dbStudent));
				structure.setMessage("new batch added to student successfully");
				structure.setStatusCode(HttpStatus.ACCEPTED.value());
				return new ResponseEntity<ResponseStructure<Student>>(structure, HttpStatus.ACCEPTED);
			}
			throw new StudentAlreadyPresentException("Cannot add student to batch");
		} else if (recStudent.isEmpty()) {
			throw new StudentNotFoundException("Invalid Student Id");
		} else {
			throw new BatchNotFoundException("Invalid Batch Id");
		}
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsByBatchId(int batchId) {
		ResponseStructure<List<Student>> structure = new ResponseStructure<>();
		List<Student> students = studentDao.findStudentsByBatchId(batchId);
		if (students.size() > 0) {
			structure.setData(students);
			structure.setMessage("List of students By BatchId");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);
		}
		throw new StudentNotFoundException("Invalid Batch Id");
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsByBatchCode(String batchCode) {
		ResponseStructure<List<Student>> structure = new ResponseStructure<>();
		List<Student> students = studentDao.findStudentsByBatchCode(batchCode);
		if (students.size() > 0) {
			structure.setData(students);
			structure.setMessage("List of students by BatchCode");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);
		}
		throw new StudentNotFoundException("Invalid Batch Code");
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsBySubject(String subject) {
		ResponseStructure<List<Student>> structure = new ResponseStructure<>();
		List<Student> students = studentDao.findStudentsBySubject(subject);
		if (students.size() > 0) {
			structure.setData(students);
			structure.setMessage("List of students by Subject");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);
		}
		throw new StudentNotFoundException("Invalid Subject");
	}

	public ResponseEntity<ResponseStructure<List<Student>>> findStudentsByTrainerName(String trainerName) {
		ResponseStructure<List<Student>> structure = new ResponseStructure<>();
		List<Student> students = studentDao.findStudentsByTrainerName(trainerName);
		if (students.size() > 0) {
			structure.setData(students);
			structure.setMessage("List of students by Trainer Name");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Student>>>(structure, HttpStatus.FOUND);
		}
		throw new StudentNotFoundException("Invalid Trainer Name");
	}

	public ResponseEntity<ResponseStructure<String>> deleteStudentById(int stdId) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		boolean deleteById = studentDao.deleteById(stdId);
		if (deleteById) {
			structure.setData("Student Found");
			structure.setMessage("Student deleted Successfully");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.FOUND);
		}
		structure.setData("Student Not Found");
		structure.setMessage("Cannot delete student");
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
}
