package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UnitsModel {

    @Id
    private int unit_Id;
    private String unit_Name;
}