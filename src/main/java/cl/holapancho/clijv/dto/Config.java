package cl.holapancho.clijv.dto;

public class Config {
    private String user;
    private String password;
    private String token;

    public Config() {
    }

    public Config(String user, String password, String token) {
        this();
        this.user = user;
        this.password = password;
        this.token = token;
    }
    
    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public String getPasswordAndToken(){
        return getPassword()+getToken();
    }

    @Override
    public String toString() {
        return "Config [user=" + user + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Config other = (Config) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

}