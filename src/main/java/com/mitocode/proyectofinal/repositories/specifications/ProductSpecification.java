package com.mitocode.proyectofinal.repositories.specifications;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mitocode.proyectofinal.dtos.ProductDto;
import com.mitocode.proyectofinal.entities.Product;

@Component
public class ProductSpecification {
	
	public Specification<Product> getByFilters(ProductDto productDto) {
		  return new Specification<Product>() {
		   /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

		@Override
		   public Predicate toPredicate(Root<Product> root, 
		                  CriteriaQuery<?> query, 
		                  CriteriaBuilder criteriaBuilder) {
			
				List<Predicate> predicates = new ArrayList<>();
				//Name
				if (StringUtils.hasLength(productDto.getName())) {
					predicates.add(
	                        criteriaBuilder.like(
	                                criteriaBuilder.lower(root.get("name")),
	                                "%" + productDto.getName().toLowerCase() + "%"
	                        )
	                );
				}
				//Description
				if (StringUtils.hasLength(productDto.getDescription())) {
					predicates.add(
	                        criteriaBuilder.like(
	                                criteriaBuilder.lower(root.get("description")),
	                                "%" + productDto.getDescription().toLowerCase() + "%"
	                        )
	                );
				}
				//Price
				if (productDto.getPrice() != null && productDto.getPrice() > 0) {
					predicates.add(
	                        criteriaBuilder.lessThan(
	                                root.get("price"),
	                                productDto.getPrice()
	                        )
	                );
				}
				//Due Date
				if (productDto.getDueDate() != null) {
					predicates.add(
	                        criteriaBuilder.lessThanOrEqualTo(
	                                root.get("dueDate"),
	                                productDto.getDueDate()
	                        )
	                );
				}
				//Enabled
				if (productDto.isEnabled()) {
					predicates.add(
	                        criteriaBuilder.equal(
	                                root.get("enabled"),
	                                productDto.isEnabled()
	                        )
	                );
				} else {
					predicates.add(
	                        criteriaBuilder.notEqual(
	                                root.get("enabled"),
	                                productDto.isEnabled()
	                        )
	                );
				}
			
			   //Order resolver
			    query.orderBy(criteriaBuilder.asc(root.get("name"))); 
			   
		     //criteriaBuilder.like(root.get("name"), "%"+productDto.getName()+"%");
		     
		     return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		   }
		  };
	
	
		  /*
		//Order resolver
          String orderByField = "titulo";
          query.orderBy(

                  filtersDTO.isASC() ?
                          criteriaBuilder.asc(root.get(orderByField)) :
                          criteriaBuilder.desc(root.get(orderByField))
          );

          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));*/
	}
}
