package hackathon.meetingroom.auth;

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String      _name;
    private final String      _password;

    public User(String name, String password) {
        _name = name;
        _password = password;
    }

    public String getName() {
        return _name;
    }

    public String getPassword() {
        return _password;
    }
}
