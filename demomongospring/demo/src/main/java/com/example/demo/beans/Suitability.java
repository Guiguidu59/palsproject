package com.example.demo.beans;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Suitability {
    @Id

    private String type;
    private String image;
    private int level;

    public Suitability(String type, String image, int level) {
        this.type = type;
        this.image = image;
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Suitability => \n" +
                "type " + type + "\n" +
                "image='" + image + "\n" +
                "level=" + level + "\n";
    }
}
