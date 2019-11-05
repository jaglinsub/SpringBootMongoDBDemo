package com.thepracticaldeveloper.reactiveweb.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "CareerCheckList")
public final class CareerCheckList extends  DefaultCareerCheckList {

//    private String id;
//    @DBRef
//    private DefaultCareerCheckList[] defaultCareerCheckLists;
    private Integer subTotals;
    private String userId;

    @DBRef
    private CareerCheckListItems careerCheckListItems;
    // Empty constructor is needed for Jackson to deserialize JSON
    public CareerCheckList() {
        super();
    }
}
