package com.example.lesure.subscription.email.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class EmailDetailsDto {

    private String recipient;
    private String subject;
    private String message;
}
