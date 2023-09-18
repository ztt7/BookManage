package book.manage.entity;

import lombok.Data;

@Data
public class Student {
    int sid;
    //这里为什么要定义为final?
    //因为数据库字段为not null ，sid可以自增
    //final加了就是会去找没有sid的构造函数
    String name;
    String sex;
    int grade;

    public Student() {
    }

    public Student(String name, String sex, int grade) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
    }
}
