package org.jsp.batchstudent.dao;

import java.util.List;
import java.util.Optional;

import org.jsp.batchstudent.dto.Batch;
import org.jsp.batchstudent.dto.Student;
import org.jsp.batchstudent.repository.BatchRepository;
import org.jsp.batchstudent.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private BatchRepository batchRepository;

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	public Optional<Student> findById(Integer id) {
		return studentRepository.findById(id);
	}

	public List<Student> findStudentsByBatchCode(String batchCode) {
		return studentRepository.findStudentByBatchCode(batchCode);
	}

	public List<Student> findStudentsByBatchId(int batchId) {
		return studentRepository.findStudentsByBatchId(batchId);
	}

	public List<Student> findStudentsByTrainerName(String trainerName) {
		return studentRepository.findStudentsByTrainerName(trainerName);
	}

	public List<Student> findStudentsBySubject(String subject) {
		return studentRepository.findStudentsBySubject(subject);
	}

	public boolean deleteById(int id) {
		// Retrieve the batch by ID
		Student student = studentRepository.findById(id).orElse(null);
		if (student != null) {
			// Remove the batch from all associated students
			for (Batch batch : student.getBatches()) {
				batch.getStudents().remove(student);
				batchRepository.save(batch); // Update the student
			}

			// Clear the relationship from the batch side
			student.getBatches().clear();
			studentRepository.save(student);
			studentRepository.delete(student);
			return true;
		}
		return false;
	}

}
