package itss.group14.timekeeper.model;

public class Officer extends Employee{
    private final String department = "officer";
//    department cố định: officer
    public Officer(String id, String name, String department) {
        super(id, name, department);
    }

}
