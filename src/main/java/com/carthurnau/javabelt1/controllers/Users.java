package com.carthurnau.javabelt1.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.carthurnau.javabelt1.models.Course;
import com.carthurnau.javabelt1.models.CourseUserJoin;
import com.carthurnau.javabelt1.models.User;
import com.carthurnau.javabelt1.services.CUJService;
import com.carthurnau.javabelt1.services.CourseService;
import com.carthurnau.javabelt1.services.UserDetailsServiceImplementation;
import com.carthurnau.javabelt1.services.UserService;
import com.carthurnau.javabelt1.validator.UserValidator;

@Controller
public class Users {
    private UserService userService;
    private UserValidator userValidator;
    private UserDetailsServiceImplementation userDetailsService;
    private CourseService courseService;
    private CUJService cujService;
        
    public Users(UserService userService, UserValidator userValidator, UserDetailsServiceImplementation userDetailsService, CourseService courseService, CUJService cujService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userDetailsService = userDetailsService;
        this.courseService = courseService;
        this.cujService = cujService;
    }
	
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session, Principal principal) {
    		System.out.println("in /registration => 1");
    		userValidator.validate(user, result);
    		System.out.println("in /registration => 1");
        Date date = new Date();
        user.setLastSignIn(date);
		System.out.println("in /registration => 1");
        System.out.println("last sign in: " + user.getLastSignIn() + " date: " + date);
        if (result.hasErrors()) {
            return "loginRegPage.jsp";
        }
        userService.saveWithUserRole(user);
                
        return "redirect:/login";
    }
 		
    @RequestMapping(value= {"/login","/registration"})
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @Valid @ModelAttribute("user") User user) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginRegPage.jsp";
    }
	
    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        String username = principal.getName();
        User currentUser = userService.findByUsername(username);
        Date date = new Date();
        currentUser.setLastSignIn(date);
        userService.save(currentUser);
                
        model.addAttribute("currentUser", userService.findByUsername(username));
		return "redirect:/courses";
    }
    
    @RequestMapping("courses/new")
    public String newCourse(@ModelAttribute("course") Course course, Model model) {
    		return "newCourse.jsp";
    }

    @RequestMapping("courses")
    public String showDashboard(Principal principal, Model model) {
        String username = principal.getName();                
        model.addAttribute("currentUser", userService.findByUsername(username));
        
        ArrayList<Course> courses = courseService.findAll();
        model.addAttribute("courses", courses);
        return "dashboard.jsp";
    }
    
    @PostMapping("courses/createCourse")
    public String createCourse(@Valid @ModelAttribute("course") Course course, BindingResult result) {
    		System.out.println("course title: " + course.getTitle());
    		if (result.hasErrors()) {
    			return "newCourse.jsp";
    		} else {
    			courseService.addCourse(course);
    			return "redirect:/courses";
    		}
    }
    
    @RequestMapping("courses/signup/{id}")
    public String addCourseToUser(@PathVariable("id") Long course_id, Principal principal) {
        String username = principal.getName();                
        User this_user = userService.findByUsername(username);
        Course this_course = courseService.findById(course_id);

        	// Add user to this course
        this_course.getUsers().add(this_user);
        courseService.save(this_course);

        
/* Mike:
 
  The add function above creates a row in the courses-users 
  join table, but I get null pointer errors if I try to do
  access the row in the join table. The user_id and course_id
  are populated, but the createdAt field is not populated.
  
  I am trying to use the middle table to store a "signedUpAt" date
  which will be unique for each user/course pair.
  
          
 */
        
        
        
        
        
        System.out.println("About to call cujService findall");
        ArrayList<CourseUserJoin> all_cujs =  cujService.findAll();
        System.out.println("got all cujs");
        
        
        System.out.println("Added user to course: " + this_user.getFullName() + " " + this_course.getTitle());
        System.out.println("About get cuj with userid: " + this_user.getId() + " and course " + this_course.getId());
        
       CourseUserJoin this_cuj = new CourseUserJoin();
       
       this_cuj = cujService.getCUJForThisCourseUser(this_user.getId(), this_course.getId());
       
       Date date = new Date();
       System.out.println(date);
       this_cuj.setSignedUpAt(date);
       System.out.println("set signed up at");

       cujService.save(this_cuj);
       System.out.println("saved the cuj");
         
        for (User user : this_course.getUsers()) {
        		System.out.println("course: " + this_course.getTitle() + " user: " + user.getFullName());
        		// Get signup date for this course and this user
        		// This is the CreateAt field in the join table CourseUserJoin
        		date = cujService.getDateForThisCourseUser(user.getId(),this_course.getId());
        		System.out.println("signup date for this course, this user");
        }

       
        return "redirect:/courses";
    }
    
    @RequestMapping("courses/showCourse/{id}")
    public String showCourse(@PathVariable("id") Long course_id, Principal principal, Model model) {
        String username = principal.getName();                
        User this_user = userService.findByUsername(username);
        Course this_course = courseService.findById(course_id);
        model.addAttribute("this_user", this_user);
        model.addAttribute("this_course", this_course);
        
        int count = 0;
        for (User user: this_course.getUsers()) {
        		count = count + 1;
        }
             
        model.addAttribute("count", count);
        return "courseDetail.jsp";
    }
    
    @PostMapping("courses/edit")
    public String editCourse(@RequestParam("courseId") Long courseId, @ModelAttribute("course") Course course, Model model) {
    		System.out.println("in editCourse");
    		Course this_course = courseService.findById(courseId);
    		System.out.println("in edit: course: " + this_course.getTitle());
        int count = 0;
        for (User user: this_course.getUsers()) {
            count = count + 1;
        }
                 
        model.addAttribute("count", count);
 
    		model.addAttribute("course", this_course);
    		return "editCourse.jsp";
    }
    
    @PostMapping("courses/update")
    public String updateCourse(@Valid @ModelAttribute("course") Course course, BindingResult result, @RequestParam("id") Long id, Model model) {
		Course this_course = courseService.findById(id);
    		if (result.hasErrors()) {
    			return "editCourse.jsp";
    		}
    		for (User user: this_course.getUsers()) {
    			course.getUsers().add(user);
    		}
    		courseService.save(course);
    		String redirect_str = "redirect:/courses/showCourse/" + course.getId();
    		return redirect_str;
    }

    @PostMapping("courses/delete")
    public String deleteCourse(@Valid @ModelAttribute("course") Course course, @RequestParam("courseId") Long id) {
    		courseService.delete(id);
    		return "redirect:/courses";
    }


}
