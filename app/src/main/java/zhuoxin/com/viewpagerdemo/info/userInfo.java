package zhuoxin.com.viewpagerdemo.info;

public class userInfo {
    public  String name;
    public  String passwd;

    public userInfo(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public String toString() {
        return "userInfo{" +
                "name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
