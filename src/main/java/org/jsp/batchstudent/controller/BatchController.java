package org.jsp.batchstudent.controller;

import java.util.List;

import org.jsp.batchstudent.dto.Batch;
import org.jsp.batchstudent.dto.ResponseStructure;
import org.jsp.batchstudent.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batches")
public class BatchController {
	@Autowired
	private BatchService batchService;

	@PostMapping("/")
	public ResponseEntity<ResponseStructure<Batch>> saveBatch(@RequestBody Batch batch) {
		return batchService.saveBatch(batch);
	}

	@PutMapping("/")
	public ResponseEntity<ResponseStructure<Batch>> updateBatch(@RequestBody Batch batch) {
		return batchService.updateBatch(batch);
	}

	@GetMapping("/find-by-student-id/{student_id}")
	public ResponseEntity<ResponseStructure<List<Batch>>> findBatchesByStudentId(@PathVariable int student_id) {
		return batchService.findBatchesByStudentId(student_id);
	}

	@GetMapping("/find-by-student-phone/{student_phone}")
	public ResponseEntity<ResponseStructure<List<Batch>>> findBatchesByStudentId(@PathVariable long student_phone) {
		return batchService.findBatchesByStudentPhone(student_phone);
	}
	
	@DeleteMapping("/delete-by-batchId/{batchId}")
	public ResponseEntity<ResponseStructure<String>> deleteByBatchId(@PathVariable int batchId) {
		return batchService.deleteBatchById(batchId);
	}

}
