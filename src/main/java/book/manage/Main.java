package book.manage;

import book.manage.entity.Book;
import book.manage.entity.Student;
import book.manage.mapper.BookMapper;
import book.manage.sql.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.security.PrivateKey;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.logging.LogManager;

@Log
public class Main {
    public static void main(String[] args) {
//        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"));
//        //try with resources 对于实现autoclonable接口的 可以不用写close
//        try (SqlSession sqlSession = factory.openSession(true)) {
//            BookMapper mapper = sqlSession.getMapper(BookMapper.class);
////            System.out.println(mapper.addStudent(new Student("小明","男",2019)));
//            System.out.println(mapper.addBook(new Book("书籍1","我是介绍",1.8)));
//        }


        try (Scanner scanner = new Scanner(System.in)){
            LogManager manager = LogManager.getLogManager();
            manager.readConfiguration(Resources.getResourceAsStream("logging.properties"));


            while (true){
                System.out.println("=======================================");
                System.out.println("1. 录入学生信息");
                System.out.println("2. 录入书籍信息");
                System.out.println("3. 添加借阅信息");
                System.out.println("4. 展示借阅信息");
                System.out.println("5. 展示学生信息");
                System.out.println("6. 展示书籍信息");
                System.out.print("输入您想要执行的操作（输入其他任意数字退出） ： ");
                int input;
                try {
                    input = scanner.nextInt();
                } catch (Exception e) {
                    return;
                }
                scanner.nextLine();//nextInt()不会把换行符扫进去，所以这里需要扫描一下换行
                switch (input) {
                    case 1:
                        addStudent(scanner);
                        break;
                        //仅当一个 case 语句中的值和 switch 表达式的值匹配时才开始执行语句，
                        // 直到 switch 的程序段结束或者遇到第一个 break 语句为止。就有一种跳出switch 语句的效果。
                    case 2:
                        addBook(scanner);
                        // 总结起来，break语句在switch语句中的作用是结束当前的case块，并跳出switch语句，执行switch后的第一个语句。
                        // 如果没有加break,程序会继续执行下一个case标号关联的语句，直到遇到break为止。
                        break;
                    case 3:
                        addBorrow(scanner);
                        break;
                    case 4:
                        showBorrow();
                        break;
                    case 5:
                        showStudent();
                        break;
                    case 6:
                        showBook();
                        break;
                    default:
                        return;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showStudent() {
        SqlUtil.doSqlWork(mapper -> {
            mapper.getStudentList().forEach(student -> {
                System.out.println(student.getSid() + ", " + student.getName() + ", " + student.getSex() + ", " + student.getGrade() + "级");
            });
        });
    }

    private static void showBook() {
        SqlUtil.doSqlWork(mapper -> {
            mapper.getBookList().forEach(book -> {
                System.out.println(book.getBid() + ", " + book.getTitle() + "," + "[" + book.getPrice() + "]" + "(" + book.getTitle() + ")");
            });
        });
    }
    private static void showBorrow() {
        SqlUtil.doSqlWork(mapper -> {
            mapper.getBorrowList().forEach(borrow -> {
                System.out.println(borrow.getStudent().getName()+ "- > " + borrow.getBook().getTitle());
            });
        });
    }
    private static void addBorrow(Scanner scanner) {
        System.out.print("请输入书籍编号：");
        String a = scanner.nextLine();
        int bid = Integer.parseInt(a);
        System.out.print("请输入学生号：");
        String b = scanner.nextLine();
        int sid = Integer.parseInt(b);
        SqlUtil.doSqlWork(mapper -> {
            mapper.addBorrow(sid, bid);
        });
    }
    private static void addStudent(Scanner scanner){
        System.out.print("请输入学生名字：");
        String name = scanner.nextLine();
        System.out.print("请输入学生性别（男/女）：");
        String sex = scanner.nextLine();
        System.out.print("请输入学生年级：");
        String grade = scanner.nextLine();
        int g = Integer.parseInt(grade);
        Student student = new Student(name, sex, g);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addStudent(student);
            if (i > 0) {
                System.out.println("学生信息录入成功！");
                log.info("新添加了一条学生信息：" + student);
            } else System.out.println("学生信息录入失败，请重试！");
        });
    }

    private static void addBook(Scanner scanner){
        System.out.print("请输入书籍标题：");
        String title = scanner.nextLine();
        System.out.print("请输入书籍介绍：");
        String desc = scanner.nextLine();
        System.out.print("请输入书籍价格：");
        String price = scanner.nextLine();
        double v = Double.parseDouble(price);
        Book book = new Book(title, desc, v);
        SqlUtil.doSqlWork(mapper -> {
            int i = mapper.addBook(book);
            if (i > 0) {
                System.out.println("书籍信息录入成功！");
                log.info("新添加了一条书籍信息：" + book);
            } else System.out.println("书籍信息录入失败，请重试！");
        });
    }
}
