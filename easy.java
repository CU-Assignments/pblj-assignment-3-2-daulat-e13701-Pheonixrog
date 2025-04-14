public class Course {
    private String courseName;
    private int duration;

    public Course(String courseName, int duration) {
        this.courseName = courseName;
        this.duration = duration;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getDuration() {
        return duration;
    }

    public String toString() {
        return "Course: " + courseName + ", Duration: " + duration + " months";
    }
}

public class Student {
    private String name;
    private Course course;

    public Student(String name, Course course) {
        this.name = name;
        this.course = course;
    }

    public void displayInfo() {
        System.out.println("Student: " + name);
        System.out.println(course);
    }
}

import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    @Bean
    public Course course() {
        return new Course("Spring Framework", 3);
    }

    @Bean
    public Student student() {
        return new Student("Rishabh", course());
    }
}

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = context.getBean(Student.class);
        student.displayInfo();
    }
}


