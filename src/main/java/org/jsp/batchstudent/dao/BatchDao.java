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
public class BatchDao {
	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private StudentRepository studentRepository;

	public Batch saveBatch(Batch batch) {
		return batchRepository.save(batch);
	}

	public Batch updateBatch(Batch batch) {
		return batchRepository.save(batch);
	}

	public Optional<Batch> findById(int batchId) {
		return batchRepository.findById(batchId);
	}

	public List<Batch> findBatchesByStudentPhone(long phone) {
		return batchRepository.findBatchesByStudentPhone(phone);
	}

	public List<Batch> findBatchesByStudentId(int id) {
		return batchRepository.findBatchesByStudentId(id);
	}

	public boolean deleteById(int id) {
		// Retrieve the batch by ID
        Batch batch = batchRepository.findById(id).orElse(null);
		if (batch != null) {
			 // Remove the batch from all associated students
            for (Student student : batch.getStudents()) {
                student.getBatches().remove(batch);
                studentRepository.save(student); // Update the student
            }

            // Clear the relationship from the batch side
            batch.getStudents().clear();
            batchRepository.save(batch);
            batchRepository.delete(batch);
			return true;
		}
		return false;
	}

	public Optional<Batch> findByBatchCode(String batchCode) {
		return batchRepository.findByBatchCode(batchCode);
		
	}

}
