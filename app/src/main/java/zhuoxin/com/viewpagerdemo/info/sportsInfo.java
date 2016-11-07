package zhuoxin.com.viewpagerdemo.info;

public class sportsInfo {
    public String ctime;
    public String title;
    public String description;
    public String picUrl;
    public String url;

    public sportsInfo(String ctime, String title, String description, String picUrl, String url) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String toString() {
        return "sportsInfo{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
