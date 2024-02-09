package com.example.demo.beans;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
public class Stats {
    @Id
    private int hp;
    private Attack attack;
    private int defense;
    private Speed speed;
    private int stamina;
    private int support;
    private int food;
}
