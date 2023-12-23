package org.jsp.batchstudent.repository;

import java.util.List;
import java.util.Optional;

import org.jsp.batchstudent.dto.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatchRepository extends JpaRepository<Batch, Integer> {

	@Query("select s.batches from Student s where s.id=?1")
	public List<Batch> findBatchesByStudentId(int id);

	@Query("select s.batches from Student s where s.phone=?1")
	public List<Batch> findBatchesByStudentPhone(long phone);

	public Optional<Batch> findByBatchCode(String BatchCode);

}
