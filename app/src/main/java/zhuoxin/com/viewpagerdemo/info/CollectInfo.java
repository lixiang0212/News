package zhuoxin.com.viewpagerdemo.info;

public class CollectInfo {

    public String title;
    public String url;
    public long id;
    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CollectInfo(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String toString() {
        return "CollectInfo{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
