package mobile.android.prototype.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UuidEntity {
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("uuid")
    @Expose
    private String uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
