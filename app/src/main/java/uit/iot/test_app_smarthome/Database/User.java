package uit.iot.test_app_smarthome.Database;

public class User {
    private static int id = 0;
    private String name;
    private String username;
    private String password;
    private static User instance;

    private User(){}

    public static User getInstance(){
        if(instance == null){
            synchronized (User.class){
                if(instance == null){
                    instance = new User();
                }
            }
        }
        return instance;
    }

    public void setUser(String name, String username, String password) {
        id++;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {return password;}

    public String getUsername() {
        return username;
    }

}
