package com.carthurnau.javabelt1.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.carthurnau.javabelt1.models.Course;
import com.carthurnau.javabelt1.models.CourseUserJoin;
import com.carthurnau.javabelt1.models.User;

public interface CUJRepository extends CrudRepository<CourseUserJoin, Long> {
	List<CourseUserJoin> findAll();
	
	@Query(value="SELECT * FROM CourseUserJoin", nativeQuery=true)
	List<CourseUserJoin> getAllViaSQL();
			
	@Query(value="SELECT signed_up_at FROM users_courses WHERE user_id=?1 AND course_id=?2", nativeQuery=true )
	Date getDateForThisCourseUser(Long uid, Long cid);

	@Query(value="SELECT * FROM users_courses WHERE user_id=?1 AND course_id=?2", nativeQuery=true )
	CourseUserJoin getCUJForThisCourseUser(Long uid, Long cid);


}
