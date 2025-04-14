import javax.persistence.*;

@Entity
public class Account {
    @Id
    private int accountId;
    private String holderName;
    private double balance;

    // Getters and Setters
}


import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private int id;
    private int fromAccount;
    private int toAccount;
    private double amount;
    private Date timestamp = new Date();

    // Getters and Setters
}
import org.hibernate.*;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public class BankDao {

    private SessionFactory sessionFactory;

    public BankDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void transferMoney(int fromId, int toId, double amount) {
        Session session = sessionFactory.getCurrentSession();

        Account from = session.get(Account.class, fromId);
        Account to = session.get(Account.class, toId);

        if (from.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds!");
        }

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        Transaction txn = new Transaction();
        txn.setFromAccount(fromId);
        txn.setToAccount(toId);
        txn.setAmount(amount);

        session.save(txn);
    }
}
@Configuration
@EnableTransactionManagement
@ComponentScan("com.bank")
public class AppConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sf) {
        return new HibernateTransactionManager(sf);
    }
}


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        BankDao dao = ctx.getBean(BankDao.class);

        try {
            dao.transferMoney(1, 2, 500.0);
            System.out.println("Transaction Successful");
        } catch (Exception e) {
            System.out.println("Transaction Failed: " + e.getMessage());
        }

        ctx.close();
    }
}




