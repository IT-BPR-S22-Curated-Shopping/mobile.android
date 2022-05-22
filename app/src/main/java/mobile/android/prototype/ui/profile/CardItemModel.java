package mobile.android.prototype.ui.profile;

public class CardItemModel {

    private String name;
    private String tags;
    private String imageUrl;


    public CardItemModel(String name, String tags, String imageUrl) {
        this.name = name;
        this.tags = tags;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
