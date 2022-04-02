package com.mitocode.proyectofinal.utils;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponse<T> {

	@ApiModelProperty(notes = "Lista de elementos encontrados", name = "contenido")
	private List<T> content;
	@ApiModelProperty(notes = "Numero de pagina a buscar", name = "numero de pagina")
	private int pageNo;
	@ApiModelProperty(notes = "Cantidad de elementos que se desean por pagina", name = "tamaño de la pagina")
	private int pageSize;
	@ApiModelProperty(notes = "Indica el total de elementos de la pagina actual", name = "total de elementos")
	private long totalElements;
	@ApiModelProperty(notes = "Indica el total de paginas de la consulta", name = "total de paginas")
	private int totalPages;
	@ApiModelProperty(notes = "Indica si es la ultima pagina", name = "ultima pagina")
	private boolean last;
}
