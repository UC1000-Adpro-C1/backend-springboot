package id.ac.ui.cs.advprog.farrel.model;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class User {
    private String name;
    private String userId
    private long balance;
    private ArrayList<TopUp> userTopUpList = new ArrayList<TopUp>();
}
