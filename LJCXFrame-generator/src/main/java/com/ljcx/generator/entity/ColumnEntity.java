package com.ljcx.generator.entity;


public class ColumnEntity {
    //列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String comments;

    //属性名称大写
    private String attrName;
    //属性名称小写
    private String attrname;
    //属性类型
    private String attrType;
    //auto_increment
    private String extra;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public ColumnEntity(){}
    public ColumnEntity(String columnName, String dataType, String comments, String attrName, String attrname, String attrType, String extra) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.comments = comments;
        this.attrName = attrName;
        this.attrname = attrname;
        this.attrType = attrType;
        this.extra = extra;
    }
}
