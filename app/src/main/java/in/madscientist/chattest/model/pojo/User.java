package in.madscientist.chattest.model.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by frapp on 11/2/17.
 */

public class User {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("image-url")
    private String image_url;
    private String username;

    private int totalMsgCount =0;
    private int favCount=0;

    public int getFavCount() {
        return favCount;
    }

    public static final int MESSAGE_TYPE_FAV=1;
    public static final int MESSAGE_TYPE_NOT_FAV=0;
    public void setFavCount(int favCount) {
        this.favCount = favCount;
    }

    public int getTotalMsgCount() {
        return totalMsgCount;
    }

    public void setTotalMsgCount(int totalMsgCount) {
        this.totalMsgCount = totalMsgCount;
    }

    public User() {
    }

    public User(String name, String image_url, String username) {
        this.name = name;
        this.image_url = image_url;
        this.username = username;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
