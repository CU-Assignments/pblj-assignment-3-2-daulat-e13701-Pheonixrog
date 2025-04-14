<hibernate-configuration>
  <session-factory>
    <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/yourdb</property>
    <property name="hibernate.connection.username">root</property>
    <property name="hibernate.connection.password">password</property>
    <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
    <property name="show_sql">true</property>
    <property name="hbm2ddl.auto">update</property>
    
    <mapping class="Student"/>
  </session-factory>
</hibernate-configuration>

  import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int age;

    // Getters and Setters
}

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


import org.hibernate.Session;
import org.hibernate.Transaction;

public class StudentDao {

    public void createStudent(Student s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(s);
        tx.commit();
        session.close();
    }

    public Student readStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student s = session.get(Student.class, id);
        session.close();
        return s;
    }

    public void updateStudent(Student s) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(s);
        tx.commit();
        session.close();
    }

    public void deleteStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student s = session.get(Student.class, id);
        session.delete(s);
        tx.commit();
        session.close();
    }
}


public class Main {
    public static void main(String[] args) {
        StudentDao dao = new StudentDao();

        Student s = new Student();
        s.setName("Rishabh");
        s.setAge(22);
        dao.createStudent(s);

        Student fetched = dao.readStudent(s.getId());
        System.out.println("Read: " + fetched.getName());

        fetched.setAge(23);
        dao.updateStudent(fetched);

        dao.deleteStudent(fetched.getId());
    }
}
