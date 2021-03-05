package com.ilyha.secsite.Controllers;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ilyha.secsite.model.Student;

@RestController
@RequestMapping("/api/v1/")
public class RESTController {

	Set<Student> STUDENTS = Set.of(new Student(1, "VITALYK"), new Student(2, "WLADIMIR"), new Student(3, "ELLIO"));

	@GetMapping("/student/{id}")
	public Student getStudent(@PathVariable("id") int id) {
		return STUDENTS.stream().filter(st -> st.getId() == id).findFirst().orElse(null);
	}

	@GetMapping("/management")
	public Set<Student> getAllStudents() {
		return STUDENTS;
	}

	@PostMapping("/management/add")
	public void addStudent(@RequestBody Student student) {
		STUDENTS.add(student);
		System.out.println("adding new Student: " + student);
	}

	@PutMapping("/management/update/{id}")
	public void updStudent(@RequestParam String name, @PathVariable("id") int id) {
		deleteStudent(id);
		STUDENTS.add(new Student(id, name));
		System.out.println("UPDATING STUDENT WITH ID(" + id + ")");
	}

	@DeleteMapping("/management/delete/{id}")
	public void deleteStudent(@PathVariable("id") int id) {
		STUDENTS.remove(STUDENTS.stream().filter(st -> st.getId() == id).findFirst()
				.orElseThrow(() -> new IllegalStateException("no such student")));
	}
}
