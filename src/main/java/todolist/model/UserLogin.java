package todolist.model;

/**
 * Created by kingfernandez on 2/8/17.
 */
public class UserLogin {
    private String idtoken;

    public UserLogin() {}

    public UserLogin(String idtoken) {
        this.idtoken = idtoken;
    }

    public String getIdtoken() {
        return idtoken;
    }

    public void setIdtoken(String idtoken) {
        this.idtoken = idtoken;
    }
}
