package com.lai.demo.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class WriteExcel {
    public static void main(String[] args) {
        //单个Sheet导出,无模型映射导出
        String filePath = "C://";//"C:\\Users\\ASUS\\Desktop\\测试导出.xlsx";
        List<List<Object>> data = new ArrayList<>();
        data.add(Arrays.asList("1111","2221","3313"));
        data.add(Arrays.asList("1112","2222","3332"));
        data.add(Arrays.asList("1113","2223","3333"));
        List<String> head = Arrays.asList("表头11", "表头21", "表头31");
        ExcelUtil.writeBySimple(filePath,data,head);
        System.out.println("无模型导出完毕："+filePath);

        //单个Sheet导出,有模型映射导出
        WriteExcel writeExcel=new WriteExcel();
       // writeExcel.exportExcrl();
        //writeExcel.writeEcxel();
       // writeExcel.test();
        //writeExcel.writeEcxel1();
    }

    public void exportExcrl(){
        String filePath = "C:\\Users\\ASUS\\Desktop\\测试导出1.xlsx";
        ArrayList<TableHeaderExcel> data = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            TableHeaderExcel TableHeaderExcel = new TableHeaderExcel();
            TableHeaderExcel.setName("cmj" + i);
            TableHeaderExcel.setAge(22 + i);
            TableHeaderExcel.setSchool("清华大学" + i);
            data.add(TableHeaderExcel);
        }
        ExcelUtil.writeWithTemplate(filePath,data);
        out.println("有模型导出完毕："+filePath);
    }

    //另一种方法写Excel
    public void writeEcxel(){
        String filePath = "C:\\Users\\ASUS\\Desktop\\养老保险在参人员花名册.xlsx";
        try {
            OutputStream out = new FileOutputStream(filePath);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 4,TableHeaderExcel.class);
            sheet1.setSheetName("第一个sheet");
            sheet1.setSheetNo(3);
            sheet1.setStartRow(2);

            Table table1 = new Table(1);
            table1.setTableStyle(WriteExcel.createTableStyle());

            //造数据
            ArrayList<TableHeaderExcel> data = new ArrayList<>();
            for(int i = 0; i < 4; i++){
                TableHeaderExcel TableHeaderExcel = new TableHeaderExcel();
                TableHeaderExcel.setName("cmj" + i);
                TableHeaderExcel.setAge(22 + i);
                TableHeaderExcel.setSchool("清华大学" + i);
                data.add(TableHeaderExcel);
            }
            writer.write1(null, sheet1, table1);
            writer.finish();
            System.out.println("有模型导出完毕："+filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void test(){
        String filePath = "C:\\Users\\ASUS\\Desktop\\医疗生育在参人员花名册.xlsx";
        List<Object> objects1 = ExcelUtil.readLessThan1000Row(filePath);
        System.out.println("读取默认Sheet1的全部数据结束");

        List<List<Object>> data  = new ArrayList<>();
        for (int i = 0; i <objects1.size() ; i++) {
            List<Object> list1=(List)objects1.get(i);
            data.add(list1);
        }
         String filePath1 = "C:\\Users\\ASUS\\Desktop\\测试导出.xlsx";
        ExcelUtil.writeBySimple(filePath1,data,null);
        System.out.println("无模型导出完毕："+filePath1);
    }

    public void writeEcxel1(){
        String filePath = "C:\\Users\\ASUS\\Desktop\\养老保险在参人员花名册5555.xlsx";
        try {
            OutputStream out = new FileOutputStream(filePath);
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);//,TableHeaderExcel.class
            sheet1.setSheetName("第一个sheet");
            sheet1.setStartRow(0);

            // 创建一个表格，用于 Sheet 中使用
            Table table1 = new Table(1);

            // 无注解的模式，动态添加表头
            table1.setHead(WriteExcel.createTestListStringHead("失业保险在职参保人员花名册","有效证件号码：","打印日期：","单位名称：","社保机构："));
            table1.setTableStyle(WriteExcel.createTableStyle());

            //造数据
            List<List<Object>> data = new ArrayList<>();
            data.add(Arrays.asList("1111","2221","3313"));
            data.add(Arrays.asList("1112","2222","3332"));
            data.add(Arrays.asList("1113","2223","3333"));
            writer.write1(data, sheet1, table1);
            writer.finish();
            System.out.println("有模型导出完毕："+filePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static List<List<String>> createTestListStringHead(String value1,String value2,String value3,String value4,String value5){
        // 模型上没有注解，表头数据动态传入
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> hc1 = new ArrayList<String>();
        List<String> hc2 = new ArrayList<String>();
        List<String> hc3 = new ArrayList<String>();
        List<String> hc4 = new ArrayList<String>();
        List<String> hc5 = new ArrayList<String>();
        List<String> hc6 = new ArrayList<String>();
        List<String> hc7 = new ArrayList<String>();

        hc1.add(value1);hc2.add(value1);hc3.add(value1);hc4.add(value1);hc5.add(value1);hc6.add(value1);hc7.add(value1);
        hc1.add(value2);hc2.add(value2);hc3.add(value2);hc4.add(value2);hc5.add(value2);hc6.add(value3);hc7.add(value3);
        hc1.add(value4);hc2.add(value4);hc3.add(value4);hc4.add(value4);hc5.add(value5);hc6.add(value5);hc7.add(value5);
        hc1.add("序号");hc2.add("证件号码");hc3.add("姓名");hc4.add("出生日期");hc5.add("失业保险参保日期");hc6.add("建立个人账户年月");hc7.add("联系电话");

        head.add(hc1);
        head.add(hc2);
        head.add(hc3);
        head.add(hc4);
        head.add(hc5);
        head.add(hc6);
        head.add(hc7);
        return head;
    }

    public static TableStyle createTableStyle() {
        TableStyle tableStyle = new TableStyle();
        // 设置表头样式
        Font headFont = new Font();
        // 字体是否加粗
        headFont.setBold(true);
        // 字体大小
        headFont.setFontHeightInPoints((short)12);
        // 字体
        headFont.setFontName("楷体");
        tableStyle.setTableHeadFont(headFont);
        // 背景色
        tableStyle.setTableHeadBackGroundColor(IndexedColors.WHITE);


        // 设置表格主体样式
        Font contentFont = new Font();
        contentFont.setBold(true);
        contentFont.setFontHeightInPoints((short)12);
        contentFont.setFontName("黑体");
        tableStyle.setTableContentFont(contentFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        return tableStyle;
    }
}
