package book.manage.mapper;

import book.manage.entity.Book;
import book.manage.entity.Borrow;
import book.manage.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

    // student(name, sex, grade) name 对应数据库里面的字段名称
    // values(#{name}, #{sex}, #{grade}) 对java bean对象里面的字段名称
    @Insert("insert into student(name, sex, grade) values(#{name}, #{sex}, #{grade})")
    int addStudent(Student student);

    //desc是关键字，要加``
    @Insert("insert into book(title, `desc`, price) values(#{title}, #{desc}, #{price})")
    int addBook(Book book);

    @Insert("insert into borrow(sid, bid) values(#{sid}, #{bid})")
    int addBorrow(@Param("sid") int sid, @Param("bid") int bid);

    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "sid", property = "student", one = @One(select = "getStudentBySid")),
            @Result(column = "bid", property = "book", one = @One(select = "getBookByBid")),
    })
    @Select("select * from borrow")
    List<Borrow> getBorrowList();

//    @Results({
//            @Result(column = "sid", property = "sid", id = true),
//            @Result(column = "name", property = "name"),
//            @Result(column = "sex", property = "sex"),
//            @Result(column = "grade", property = "grade")
//    })
    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);

    @Select("select * from book where bid = #{bid}")
    Book getBookByBid(int bid);

}
