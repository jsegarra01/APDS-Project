package model.tables;

public class Pirate {

    private final String name;
    private final int age;
    private final String role;

    public Pirate(String name, int age, String role){
        this.name = name;
        this.age = age;
        this.role = role;
    }

    public String getName(){return name;}

    public int getAge(){return age;}

    public String getRole(){return role;}
}
