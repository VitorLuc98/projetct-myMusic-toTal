package com.ciandt.summit.bootcamp2022.controllers.exception;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {

   private LocalDateTime timestamp;
   private Integer status;
   private String error;
   private String path;
}
