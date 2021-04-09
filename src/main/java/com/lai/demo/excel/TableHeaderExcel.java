package com.lai.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TableHeaderExcel extends BaseRowModel {
    /**
     * value: 表头名称
     * index: 列的号, 0表示第一列
     */
    @ExcelProperty(value = {"在库货源","产地"}, index = 0)
    private String name;

    @ExcelProperty(value = {"在库货源","年龄"},index = 1)
    private int age;

    @ExcelProperty(value = {"在库货源","年龄"},index = 2)
    private String school;

}
