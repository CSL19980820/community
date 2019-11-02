package csl.individual.community.login.model;

import lombok.Data;

@Data
public class AccessModel {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
