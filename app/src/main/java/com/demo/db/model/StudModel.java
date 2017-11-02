package com.demo.db.model;

public class StudModel {

  public static final String TAG = StudModel.class.getSimpleName();
  public static final String TABLE_NAME = "studPersonal";
  public static final String COL_ID = "id";
  public static final String COL_NAME = "name";
  public static final String COL_ADD = "address";
  public static final String COL_GENDER = "gender";
  public static final String COL_PHONE = "phone";
  int id = 0;
  String name = null, add = null, gender = null, phone = null;

  public StudModel(int id, String name, String add, String gender, String phone) {
    this.id = id;
    this.name = name;
    this.add = add;
    this.gender = gender;
    this.phone = phone;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAdd() {
    return add;
  }

  public void setAdd(String add) {
    this.add = add;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
}
