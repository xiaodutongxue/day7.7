package com.ujiuye.dao;

import com.ujiuye.bean.Student;
import com.ujiuye.util.ThreadDruidUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class StuDao {
    // 创建数据库操作核心对象QueryRunner
    private QueryRunner qr = new QueryRunner(ThreadDruidUtils.getDataSource());

    // 根据学生编号获取学生信息
    public Student  getStuById(String sid){
        String sql = "select * from stu where sid = ? ";
        Student student = null;
        try {
            student = qr.query(sql, new BeanHandler<>(Student.class), sid);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return student;
    }

    /**
     * 完成学生的批量删除
     * @param ids 学生编号
     * @return
     */
    public int batchDeleteStudentById(String ids){ // 1,2,3,4
        String sql = "delete from stu where sid in(" + ids + ")";
        System.out.println(sql);
        int row = 0;
        try {
            row = qr.update(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * 添加学生到数据库
     * @Param student
     * @return 影响行数
     */
    public int addStu(Student student){
        String sql = "insert into stu values(null,?,?,?,?,?)";
        int row = 0;
        try {
            row = qr.update(sql, student.getSname(), student.getGender(),
                    student.getSbir(), student.getHobby(), student.getPhoto());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return row;
    }

    /**
     * 统计符合条件的学生人数
     * @param student 查询条件
     * @return 返回总条数
     */
    public long countStu(Student student)  {
        // sql语句
        StringBuilder sb = new StringBuilder("select count(sid) from stu where 1=1 ");
        // 查询条件的方法
        sqlCondtion(student, sb);

        String sql = sb.toString();
        System.out.println("countStu = " + sql);

        long lo = 0;
        try {
            lo = qr.query(sql, new ScalarHandler<>());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

        return lo;
    }

    /**
     * sql 条件的封装
     * @param student 条件
     * @param sb
     */
    private void sqlCondtion(Student student, StringBuilder sb) {
        String sname = student.getSname();
        if(sname != null && sname.trim() != ""){
            sb.append(" and sname like '%" + sname + "%'");
        }
        String gender = student.getGender();
        if(gender != null && gender.trim() != ""){
            sb.append(" and gender = '" + gender + "'"); // and gender = '男'
        }
    }

    /**
     * 查询学生分页数据
     * @param student 查询条件的封装
     * @param start 开始位置
     * @param num 获取的条数
     * @return 查询的学生列表 ctrl + alt + M
     */
    public List<Student> selectStudent(Student student,int start,int num){
        // sql的语句的拼接
        StringBuilder sb = new StringBuilder("select * from stu where 1=1 ");
        sqlCondtion(student,sb); // 条件的拼接
        sb.append(" limit ?,?");
        String sql = sb.toString();
        System.out.println("selectStudent = " + sql);
        List<Student> stuList = null;
        try {// 查询分页的学生信息
            stuList = qr.query(sql, new BeanListHandler<>(Student.class), start, num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stuList;
    }

    public int updateStudent(Student student) {
        String sql = "update stu set sname = ? ,gender = ? ,sbir = ? , hobby = ? , photo = ? where sid = ? ";
        int row = 0;
        try {
            row = qr.update(sql, student.getSname(), student.getGender(), student.getSbir(), student.getHobby(), student.getPhoto(), student.getSid());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return row;
    }

}
