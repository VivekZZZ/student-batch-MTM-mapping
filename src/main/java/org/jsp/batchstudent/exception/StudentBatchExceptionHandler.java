package org.jsp.batchstudent.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import org.jsp.batchstudent.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentBatchExceptionHandler {
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleBNF(BatchNotFoundException e) {

		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("No Batch Found");
		structure.setMessage(e.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleSNFE(StudentNotFoundException e) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Student Not Found");
		structure.setMessage(e.getMessage());
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleSAPE(StudentAlreadyPresentException e) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Student Already Present");
		structure.setMessage(e.getMessage());
		structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ResponseStructure<String>> handleSICVE(SQLIntegrityConstraintViolationException e) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Duplicate Entry");
		structure.setMessage(e.getMessage());
		structure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.BAD_REQUEST);
	}
	

}
