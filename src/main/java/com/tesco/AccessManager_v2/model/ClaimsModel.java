package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ClaimsModel {
    public String jti;
    public String iss;
    public String sub;
    public int iat;
    public int nbf;
    public int exp;
    public String scope;
    public int confidence_level;
    public String client_id;
    public String token_type;
}
