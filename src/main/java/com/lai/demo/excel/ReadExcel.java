package com.lai.demo.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReadExcel {
    /*public static void main(String[] args) {
        //读取默认Sheet1的全部数据(以下都是小于1000行得数据，大于10000使用readMoreThan1000Row方法)
        String filePath = "C:\\Users\\ASUS\\Desktop\\医疗生育在参人员花名册.xlsx";
        List<Object> objects1 = ExcelUtil.readLessThan1000Row(filePath);
        System.out.println("读取默认Sheet1的全部数据结束");

        //第一个1代表sheet1, 第二个1代表从第几行开始读取数据，行号最小值为0
        Sheet sheet1 = new Sheet(1, 1);
        List<Object> objects2 = ExcelUtil.readLessThan1000RowBySheet(filePath,sheet1);
        System.out.println("读取Sheet1的指定行开始数据结束");

        //读取Sheet2的全部数据
        Sheet sheet2 = new Sheet(2, 0);
        List<Object> objects3 = ExcelUtil.readLessThan1000RowBySheet(filePath,sheet2);
        System.out.println("读取Sheet2的全部数据结束");
    }*/


    public static void main(String[] args) {
       try {
           Map<String, Object> row1 = new LinkedHashMap<>();
           row1.put("姓名", "张三");
           row1.put("年龄", 23);
           row1.put("成绩", 88.32);
           row1.put("是否合格", true);
           row1.put("考试日期", DateUtil.date());

           Map<String, Object> row2 = new LinkedHashMap<>();
           row2.put("姓名", "李四");
           row2.put("年龄", 33);
           row2.put("成绩", 59.50);
           row2.put("是否合格", false);
           row2.put("考试日期", DateUtil.date());

           ArrayList<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);


           // 通过工具类创建writer
           ExcelWriter writer = ExcelUtil.getWriter("d:/writeMapTest.xlsx");
           // 合并单元格后的标题行，使用默认标题样式
           writer.merge(rows.size() - 1, "一班成绩单");
           // 一次性写出内容，使用默认样式，强制输出标题
           writer.write(rows, true);
           // 关闭writer，释放内存
           writer.close();

       }catch (Exception e){

       }
    }

}
