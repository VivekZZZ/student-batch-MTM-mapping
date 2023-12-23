package org.jsp.batchstudent.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String subject;
	@Column(nullable = false, unique = true)
	private String batchCode;
	@Column(nullable = false)
	private String trainer;

	
	@ManyToMany(mappedBy = "batches")
	private Set<Student> students;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getTrainer() {
		return trainer;
	}

	public void setTrainer(String trainer) {
		this.trainer = trainer;
	}

	public Set<Student> getStudents() {
		return students;
	}

	@JsonIgnore
	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
