package com.ciandt.summit.bootcamp2022.controllers.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {
   @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
   private LocalDateTime timestamp;
   private Integer status;
   private String error;
   private String path;
}
