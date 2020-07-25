package com.example.sample;

public class User
{
    public static final String TABLE_NAME = "user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_HEIGHT = "height";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_BMI = "bmi";


    private int id;
    private String name;
    private String weight;
    private String height;
    private String age;
    String bmi;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_WEIGHT + " TEXT,"
                    + COLUMN_HEIGHT + " TEXT,"
                    + COLUMN_AGE + " TEXT,"
                    + COLUMN_BMI + " TEXT"
                    + ")";

    public User()
    {
    }

    public User(int id, String name, String weight, String height, String age, String bmi)
    {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.bmi = bmi;
    }

    //getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getAge() {
        return age;
    }

    public String getBmi() {
        return bmi;
    }

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }
}
