package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Locations {
    public Location location;
    public String link;
    public AuditModel audit;
}
