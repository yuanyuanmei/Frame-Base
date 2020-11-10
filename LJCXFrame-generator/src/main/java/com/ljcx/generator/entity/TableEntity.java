package com.ljcx.generator.entity;


import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

public class TableEntity {
    //表的名称
    private String tableName;
    //表备注
    private String comments;
    //表主键
    private ColumnEntity pk;
    //表的列名（不包含主键）
    private List<ColumnEntity> columns;

    //类名大写
    private String className;
    //类名小写
    private String classname;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public TableEntity(){}
    public TableEntity(String tableName, String comments, ColumnEntity pk, List<ColumnEntity> columns, String className, String classname) {
        this.tableName = tableName;
        this.comments = comments;
        this.pk = pk;
        this.columns = columns;
        this.className = className;
        this.classname = classname;
    }
}
