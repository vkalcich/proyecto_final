package com.mitocode.proyectofinal.dtos;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDto {

	@ApiModelProperty(notes = "Estado HTTP del error", name = "estado")
    private HttpStatus status;
	
	@ApiModelProperty(notes = "Mensaje del error", name = "mensaje")
    private String message;
	
	@ApiModelProperty(notes = "Lista de errores encontrados", name = "errores")
    private List<String> errores;
    
}
