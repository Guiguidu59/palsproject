package com.example.demo.beans;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Aura {
    @Id
    private String name;
    private String description;
    private String tech;

    public Aura(String name, String description, String tech) {
        this.name = name;
        this.description = description;
        this.tech = tech;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTech() {
        return tech;
    }

    public void setTech(String tech) {
        this.tech = tech;
    }

    @Override
    public String toString() {
        return "Aura => \n" +
                "name: " + name + "\n" +
                "description: " + description + "\n" +
                "tech: " + tech;
    }
}
