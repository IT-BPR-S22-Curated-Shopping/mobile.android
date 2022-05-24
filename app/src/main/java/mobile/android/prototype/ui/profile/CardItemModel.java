package mobile.android.prototype.ui.profile;

import java.util.ArrayList;
import java.util.List;

import mobile.android.prototype.data.models.TagEntity;

public class CardItemModel {

    private String name;
    private List<TagEntity> tags;
    private String imageUrl;


    public CardItemModel(String name, List<TagEntity> tags, String imageUrl) {
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

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String toTagString() {
        List<String> tagArray = new ArrayList<>();
        for(TagEntity tag : tags) {
            tagArray.add(tag.getTag());
        }
        return String.join(", ", tagArray);
    }

}
