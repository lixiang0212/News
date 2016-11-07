package zhuoxin.com.viewpagerdemo.info;

import java.io.Serializable;
import java.util.List;

public class ContentlistBean implements Serializable{

    public String channelId;
    public String channelName;
    public String content;
    public String desc;
    public String html;
    public String link;
    public String nid;
    public String pubDate;
    public int sentiment_display;
    public String source;
    public String title;
    public List<imageInfo> imageurls;

    public  static  class imageInfo implements Serializable{
        public int height;
        public int width;
        public String url;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getSentiment_display() {
        return sentiment_display;
    }

    public void setSentiment_display(int sentiment_display) {
        this.sentiment_display = sentiment_display;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<imageInfo> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<imageInfo> imageurls) {
        this.imageurls = imageurls;
    }
}
