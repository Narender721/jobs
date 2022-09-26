package com.tesco.AccessManager_v2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthorizationModel {
    public String access_token;
    public String token_type;
    public int expires_in;
    public String scope;
    public int confidence_level;
    public ClaimsModel claims;
}




