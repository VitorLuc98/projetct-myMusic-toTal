package com.ciandt.summit.bootcamp2022.dto;

import com.ciandt.summit.bootcamp2022.model.User;
import com.ciandt.summit.bootcamp2022.model.UserType;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTypeDto {
    private String id;
    private String description;
    private User user;
    private UserType userType;





}
