package projectgym.gui;

public class Member {
    private final String id;
    private final String name;
    private final String age;
    private final String birthdate;
    private final String plan;
    private final String price;
    private final String joinDate;

    public Member(String id, String name, String age, String birthdate, String plan, String price, String joinDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birthdate = birthdate;
        this.plan = plan;
        this.price = price;
        this.joinDate = joinDate;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAge() { return age; }
    public String getBirthdate() { return birthdate; }
    public String getPlan() { return plan; }
    public String getPrice() { return price; }
    public String getJoinDate() { return joinDate; }
}