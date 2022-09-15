package com.ciandt.summit.bootcamp2022.controllers.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {

   private String timestamp;
   private Integer status;
   private String error;
   private String path;
}
