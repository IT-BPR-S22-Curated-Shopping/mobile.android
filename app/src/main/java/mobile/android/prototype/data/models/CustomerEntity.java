package mobile.android.prototype.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CustomerEntity {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("uuids")
    @Expose
    private List<UuidEntity> uuids = null;
    @SerializedName("tags")
    @Expose
    private List<Object> tags = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UuidEntity> getUuids() {
        return uuids;
    }

    public void setUuids(List<UuidEntity> uuids) {
        this.uuids = uuids;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", uuids=" + uuids +
                ", tags=" + tags +
                '}';
    }
}
