package com.carthurnau.javabelt1.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.carthurnau.javabelt1.models.CourseUserJoin;
import com.carthurnau.javabelt1.repositories.CUJRepository;

@Service
public class CUJService {
	private CUJRepository cujRepo;
	public void CUJSerivce(CUJRepository cujRepo) {
		this.cujRepo = cujRepo;
	}
	
	public Date getDateForThisCourseUser(Long user_id, Long course_id) {
		return cujRepo.getDateForThisCourseUser(user_id, course_id);
	}
	
	public CourseUserJoin getCUJForThisCourseUser(Long user_id, Long course_id) {
		return cujRepo.getCUJForThisCourseUser(user_id, course_id);
	}

	public void save(CourseUserJoin this_cuj) {
		cujRepo.save(this_cuj);
		
	}

	public ArrayList<CourseUserJoin> findAll() {
		System.out.println("in cujService findAll");
		return (ArrayList<CourseUserJoin>) cujRepo.findAll();
	}

	public ArrayList<CourseUserJoin> getAllViaSQL(){
		return (ArrayList<CourseUserJoin>) cujRepo.getAllViaSQL();
	}
}
