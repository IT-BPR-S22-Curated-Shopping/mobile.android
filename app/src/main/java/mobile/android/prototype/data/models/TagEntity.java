package mobile.android.prototype.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagEntity {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("tag")
    @Expose
    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
