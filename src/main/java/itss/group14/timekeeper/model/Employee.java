package itss.group14.timekeeper.model;

public class Employee {
    private String name;
    private String id;
    private String role;
    private String department;
    private int gender;

    public Employee(String name, String id, String role, String department, int gender) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.department = department;
        this.gender = gender;
    }

    public Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
