package com.carthurnau.javabelt1.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.carthurnau.javabelt1.models.Course;
import com.carthurnau.javabelt1.repositories.CourseRepository;

@Service
public class CourseService {
	private CourseRepository cRepo;
	public CourseService(CourseRepository cRepo) {
		this.cRepo = cRepo;
	}
	
	public ArrayList<Course> findAll() {
		return (ArrayList<Course>) cRepo.findAll();
	}
	
	public void addCourse(Course course) {
		cRepo.save(course);
	}
	
	public Course findById(Long id) {
		Course course = cRepo.findById(id);
		return course;
	}

	public void save(Course course) {
		cRepo.save(course);		
	}

	public Course findByTitle(String title) {
		cRepo.findByTitle(title);
		return null;
	}

	public void delete(Long id) {
		cRepo.delete(id);
	}
}
