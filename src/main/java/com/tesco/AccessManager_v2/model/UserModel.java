package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Collection("Users")
@NotNull
public class UserModel {

    @Id
    private int user_Id;
    @Field
    private String user_Name;
    @Field("UnitsModel")
    UnitsModel unitsModel;
}
