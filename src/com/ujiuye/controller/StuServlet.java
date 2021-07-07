package com.ujiuye.controller;

import com.ujiuye.bean.Page;
import com.ujiuye.bean.Student;
import com.ujiuye.service.StuService;
import com.ujiuye.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@MultipartConfig // 告诉Servlet需要文件上传操作
@WebServlet("/stu")
public class StuServlet extends BaseServlet {

    // 完成修改
    public void updateDo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sid = request.getParameter("sid");
        // 数据的获取
        String sname = request.getParameter("sname");
        String gender = request.getParameter("gender");
        String sbir = request.getParameter("sbir");
        // 获取复选框中的数据
        String[] hobby = request.getParameterValues("hobby");
        // 将数组爱好转成字符串
        String hobbys = Arrays.toString(hobby);
        // 旧头像名字
        String oldPhoto = request.getParameter("oldPhoto");
        // 获取上传的文件组件
        Part part = request.getPart("file");
        // 获取上传文件的名称,tomcat 8.0 后才会支持
        String filename = part.getSubmittedFileName();

        System.out.println("filename = " + filename);
        if(filename.trim() != ""){ // 表示没有修改头像
            //生成文件名称,使用UUID并转成字符串
            String uuid = UUID.randomUUID().toString();
            // 完整文件名称,且唯一
            oldPhoto = uuid + filename;
            // 本地磁盘的保存文件的路径
            String path = "E:\\upload";
            // 文件在本地磁盘保存的完成路径
            //  File.separator 文件夹分隔符常量
            String filepath = path + "/" + oldPhoto;
            System.out.println("filepath = " + filepath);
            // 将文件写入磁盘
            part.write(filepath);
        }

        // 数据的封装
        Student student = new Student(Integer.parseInt(sid), sname, gender,
                DateUtil.strTranferDate(sbir, "yyyy-MM-dd"), hobbys, oldPhoto);
        // 创建业务逻辑层对象
        StuService ss = new StuService();
        boolean bo = ss.updateStudent(student);
        //跳转到主页面
        response.sendRedirect("stu?method=queryStu");

    }
    // 跳转修改页面
    public void updateStudentById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String sid = request.getParameter("sid");
        // 交给业务逻辑层处理
        StuService stuService = new StuService();
        Student student = stuService.getStuById(sid);

        // 查询并回显要修改的学生信息
        request.setAttribute("stu",student);
        request.getRequestDispatcher("updateStu.jsp").forward(request,response);
    }

    // 学生的批量删除
    public void BatchDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String ids = request.getParameter("ids");
        // 交给业务逻辑层处理
        StuService stuService = new StuService();
        // 批量删除
        boolean bo = stuService.batchDeleteStudentById(ids);
        // 页面跳转
        response.sendRedirect("stu?method=queryStu");

    }

    // 查询学生信息并分页
    public void queryStu(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 参数获取
        String sname = request.getParameter("sname");
        String gender = request.getParameter("gender");
        String currentPage = request.getParameter("currentPage");
        // 数据的封装
        Student student = new Student(null,sname,gender,null,null,null);
        // 交给业务逻辑层处理
        StuService stuService = new StuService();
        // 获取学生分页信息
        Page<Student> page = stuService.selectStudentPage(currentPage, student);
        // 将学生分页信息存放到作用域中
        request.setAttribute("page",page);
        // 回显查询条件
        request.setAttribute("student",student);
        // 实现页面跳转
        request.getRequestDispatcher("queryStu.jsp").forward(request,response);
    }


    // 添加学生
    public void addStu(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 数据的获取
        String sname = request.getParameter("sname");
        String gender = request.getParameter("gender");
        String sbir = request.getParameter("sbir");
        // 获取复选框中的数据
        String[] hobby = request.getParameterValues("hobby");
        // 将数组爱好转成字符串
        String hobbys = Arrays.toString(hobby);
        // 旧头像名字

        // 获取上传的文件组件
        Part part = request.getPart("file");
        // 获取上传文件的名称,tomcat 8.0 后才会支持
        String filename = part.getSubmittedFileName();

        System.out.println("filename = " + filename);
        //生成文件名称,使用UUID并转成字符串
        String uuid = UUID.randomUUID().toString();
        // 完整文件名称,且唯一
        filename = uuid + filename;
        // 本地磁盘的保存文件的路径
        String path = "E:\\upload";
        // 文件在本地磁盘保存的完成路径
        //  File.separator 文件夹分隔符常量
        String filepath = path + "/" + filename;
        System.out.println("filepath = " + filepath);
        // 将文件写入磁盘
        part.write(filepath);


        // 数据的封装
        Student student = new Student(null, sname, gender,
                DateUtil.strTranferDate(sbir, "yyyy-MM-dd"), hobbys, filename);
        // 创建业务逻辑层对象
        StuService ss = new StuService();
        boolean bo = ss.addStu(student);
        if (bo) {
            // 成功跳转到 学生信息页面
            response.sendRedirect("stu?method=queryStu");
        }else{
            // 失败跳转到错误页面
            request.getRequestDispatcher("adderror.jsp").forward(request,response);
        }


    }

}
