package com.mitocode.proyectofinal.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageResponseBuilder<T, U> {
	
	public PageResponse<U> createPageResponse(Page<T> page, PageResponse<U> pageResponse, List<U> lista) {
		
		pageResponse.setContent(lista);
		pageResponse.setLast(page.isLast());
		pageResponse.setPageNo(page.getNumber());
		pageResponse.setPageSize(page.getSize());
		pageResponse.setTotalElements(page.getTotalElements());
		pageResponse.setTotalPages(page.getTotalPages());
		
		return pageResponse;
	}

}
