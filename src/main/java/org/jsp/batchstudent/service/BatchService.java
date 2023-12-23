package org.jsp.batchstudent.service;

import java.util.List;
import java.util.Optional;

import org.jsp.batchstudent.dao.BatchDao;
import org.jsp.batchstudent.dto.Batch;
import org.jsp.batchstudent.dto.ResponseStructure;
import org.jsp.batchstudent.exception.BatchNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BatchService {
	@Autowired
	private BatchDao batchDao;

	public ResponseEntity<ResponseStructure<Batch>> saveBatch(Batch batch) {
		ResponseStructure<Batch> structure = new ResponseStructure<>();
		Optional<Batch> findByBatchCode = batchDao.findByBatchCode(batch.getBatchCode());

		if (findByBatchCode.isPresent()) {
			// Batch with the same code already exists, handle accordingly (e.g., return
			// conflict status)
			structure.setData(findByBatchCode.get());
			structure.setMessage("Duplicate Entry");
			structure.setStatusCode(HttpStatus.CONFLICT.value());
			return new ResponseEntity<ResponseStructure<Batch>>(structure, HttpStatus.CONFLICT);
		}
		Batch dbBatch = batchDao.saveBatch(batch);
		structure.setData(dbBatch);
		structure.setMessage("Batch Saved");
		structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<Batch>>(structure, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<Batch>> updateBatch(Batch batch) {
		ResponseStructure<Batch> structure = new ResponseStructure<>();
		Optional<Batch> recBatch = batchDao.findById(batch.getId());
//		Batch recBatch = batchDao.findById(batch.getId()).orElse(null);
		if (recBatch.isPresent()) {
			Batch dbBatch = recBatch.get();
			dbBatch.setBatchCode(batch.getBatchCode());
			dbBatch.setSubject(batch.getSubject());
			dbBatch.setTrainer(batch.getTrainer());
			structure.setData(batchDao.saveBatch(dbBatch));
			structure.setMessage("Batch Updated");
			structure.setStatusCode(HttpStatus.ACCEPTED.value());
			return new ResponseEntity<ResponseStructure<Batch>>(structure, HttpStatus.ACCEPTED);
		}
		throw new BatchNotFoundException("Invalid Batch Id");
//		return null;
	}

	public ResponseEntity<ResponseStructure<List<Batch>>> findBatchesByStudentId(int stdId) {
		ResponseStructure<List<Batch>> structure = new ResponseStructure<>();
		List<Batch> batches = batchDao.findBatchesByStudentId(stdId);
		if (batches.size() > 0) {
			structure.setData(batches);
			structure.setMessage("List of batches by student id");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Batch>>>(structure, HttpStatus.FOUND);
		}
		throw new BatchNotFoundException("Invalid Student Id");
	}

	public ResponseEntity<ResponseStructure<List<Batch>>> findBatchesByStudentPhone(long phone) {
		ResponseStructure<List<Batch>> structure = new ResponseStructure<>();
		List<Batch> batches = batchDao.findBatchesByStudentPhone(phone);
		if (batches.size() > 0) {
			structure.setData(batches);
			structure.setMessage("List of batches by student phone");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<List<Batch>>>(structure, HttpStatus.FOUND);
		}
		throw new BatchNotFoundException("Invalid Student Phone Number");
	}

	public ResponseEntity<ResponseStructure<String>> deleteBatchById(int batchId) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		boolean deleteById = batchDao.deleteById(batchId);
		if (deleteById) {
			structure.setData("Batch Found");
			structure.setMessage("Batch deleted Successfully");
			structure.setStatusCode(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.FOUND);
		}
		structure.setData("Batch Not Found");
		structure.setMessage("Cannot delete batch");
		structure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

}
