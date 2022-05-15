package com.wf.nps.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wf.nps.repo.NamePronunciation;
import com.wf.nps.repo.NamePronunciationJdbcRepository;

import io.micrometer.core.instrument.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NamePronunciationController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NamePronunciationJdbcRepository namePronunciationJdbcRepository;

	@GetMapping("/names")
	@Operation(summary = "Get All Wells Fargo Employee Names Details")
	public ResponseEntity<List<NamePronunciation>> retrieveAllNames() {
		try {
			List<NamePronunciation> list = namePronunciationJdbcRepository.findAll();
			if (list.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/names/{id}")
	@Operation(summary = "Get a Wells Fargo Employee Name and Pronunciation Details by ID")
	public ResponseEntity<NamePronunciation> retrieveName(@PathVariable long id) {
		try {
			NamePronunciation nps = namePronunciationJdbcRepository.findById(id);
			if (nps == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
			if (nps.getPrefPronunciation() == null) {
				SpeechSynthesis speechSynthesis = new SpeechSynthesis();
				if (StringUtils.isBlank(nps.getPrefName())) {
					nps.setPrefPronunciation(
							speechSynthesis.getTextToSpeech(nps.getLegalFName() + nps.getLegalLName()));
				} else {
					nps.setPrefPronunciation(speechSynthesis.getTextToSpeech(nps.getPrefName()));
				}
			}
			return new ResponseEntity<>(nps, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/names/{id}")
	@Operation(summary = "Delete a Wells Fargo Employee Name and Pronunciation Details by ID")
	public ResponseEntity<String> deleteName(@PathVariable long id) {
		try {
			int result = namePronunciationJdbcRepository.deleteById(id);
			if (result > 0) {
				return new ResponseEntity<>("Employee Name Pronunciation was deleted successfully.", HttpStatus.OK);
			}
			return new ResponseEntity<>("Cannot find Employee with id=" + id, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Exception in Delete: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/names")
	@Operation(summary = "Create a Wells Fargo Employee Name and Pronunciation Details")
	public ResponseEntity<String> createName(@RequestBody NamePronunciation name) {
		try {
			name.setCreatedBy(name.getRole());
			name.setCreatedOn(new Date());
			int result = namePronunciationJdbcRepository.insert(name);
			if (result > 0) {
				return new ResponseEntity<>("Employee Name Pronunciation was created successfully.",
						HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Create Employee Failed", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Exception in Create: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/names/{id}")
	@Operation(summary = "Update a Wells Fargo Employee Name and Pronunciation Details by ID")
	public ResponseEntity<String> updateName(@RequestBody NamePronunciation name, @PathVariable long id) {
		try {
			NamePronunciation employee = namePronunciationJdbcRepository.findById(id);
			if (employee != null) {
				employee.setId(id);
				employee.setPrefPronunciation(name.getPrefPronunciation());
				employee.setUpdatedBy(name.getRole());
				employee.setUpdatedOn(new Date());
				namePronunciationJdbcRepository.update(employee);
				return new ResponseEntity<>("Employee Name Pronunciation was updated successfully.", HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Cannot find Employee with id=" + id, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Exception in Update: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
