package com.ujiuye.service;

import com.ujiuye.bean.Page;
import com.ujiuye.bean.Student;
import com.ujiuye.dao.StuDao;

import java.util.List;

public class StuService {
    // 创建一个stuDao对象
    private StuDao sd = new StuDao();

    public Student getStuById(String sid){

        if(sid != null && sid.trim() != ""){
            return sd.getStuById(sid);
        }
        return null;
    }

    public boolean batchDeleteStudentById(String ids){
        if(ids != null && ids.trim() != ""){
            int row = sd.batchDeleteStudentById(ids);
            return row > 0;
        }
        return false;
    }

    /**
     * 添加学生业务逻辑层方法
     * @param student 学生
     * @return 是否添加成功
     */
    public boolean addStu(Student student){
        if(student != null){
            // 添加学生
            int row = sd.addStu(student);
            return row > 0;
        }
        return false;
    }

    /**
     * 业务逻辑层处理分页逻辑
     * @param currentpage 当前页
     * @param student 条件
     * @return 页面数据
     */
    public Page<Student> selectStudentPage(String currentpage,Student student){
        // 统计总共有多少条数据
        long count = sd.countStu(student);
        int initSize = 3; //没页显示的条数
        int current = 1; // 默认当前页的值
        // 对当前页的处理
        if(currentpage != null && currentpage.trim() != ""){
            current = Integer.parseInt(currentpage); // 将字符串转成数字
        }
        // 起始页计算方法,当前页-1 乘以 每页显示条数
        int start = (current - 1) * initSize;
        // 获取页面数据
        List<Student> stuList = sd.selectStudent(student, start, initSize);
        // 对页面数据的封装
        Page<Student> stuPage = new Page<>(3, (int) count, current, stuList);
        return stuPage;
    }

    public boolean updateStudent(Student student) {
        int stu = sd.updateStudent(student);
        return stu > 0;
    }
}
