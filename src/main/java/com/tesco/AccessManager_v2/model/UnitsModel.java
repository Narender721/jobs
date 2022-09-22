package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.repository.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Collection("Units")
@NotNull
public class UnitsModel {

    @Id
    private int unit_Id;
    @Field
    private String unit_Name;
}