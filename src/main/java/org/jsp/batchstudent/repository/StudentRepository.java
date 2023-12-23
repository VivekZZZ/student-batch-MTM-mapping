package org.jsp.batchstudent.repository;

import java.util.List;

import org.jsp.batchstudent.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	@Query("select b.students from Batch b where b.batchCode=?1")
	public List<Student> findStudentByBatchCode(String batchCode);

	@Query("select b.students from Batch b where b.id =?1")
	public List<Student> findStudentsByBatchId(int id);
	
	@Query("select b.students from Batch b where b.trainer =?1")
	public List<Student> findStudentsByTrainerName(String trainer);

	@Query("select b.students from Batch b where b.subject =?1")
	public List<Student> findStudentsBySubject(String subject);

}
