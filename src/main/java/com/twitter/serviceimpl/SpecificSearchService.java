package com.twitter.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.twitter.dto.SearchRequestDto;
import com.twitter.dto.SpecificRequestDto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class SpecificSearchService<T> {

	public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {
		System.out.println(searchRequestDto + "=================================");
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
			}
		};
	}
	
	public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDto,SpecificRequestDto.GlobalOperator globalOperator) {
		System.out.println(searchRequestDto + "=================================");
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates= new ArrayList<>();
			for (SearchRequestDto searchRequestDto2 : searchRequestDto) {
				
				switch(searchRequestDto2.getOperation()) {
				case EQUAL:
				Predicate equal=criteriaBuilder.equal(root.get(searchRequestDto2.getColumn()), searchRequestDto2.getValue());
				predicates.add(equal);
				break;
				case LIKE:
				Predicate like=criteriaBuilder.like(root.get(searchRequestDto2.getColumn()), "%"+searchRequestDto2.getValue()+"%");
				predicates.add(like);
				break;
				case IN:
				String[] split =searchRequestDto2.getValue().split(",");
				Predicate in=root.get(searchRequestDto2.getColumn()).in(Arrays.asList(split));
				predicates.add(in);
				break;
				case GREATER_THAN:
				Predicate greaterThan=criteriaBuilder.greaterThan(root.get(searchRequestDto2.getColumn()), searchRequestDto2.getValue());
				predicates.add(greaterThan);
				break;
				case LESS_THAN:
				Predicate lessThan=criteriaBuilder.lessThan(root.get(searchRequestDto2.getColumn()), searchRequestDto2.getValue());
				predicates.add(lessThan);
				break;
				case BETWEEN:
				String[] split2 =searchRequestDto2.getValue().split(",");
				Predicate between=criteriaBuilder.between(root.get(searchRequestDto2.getColumn()),split2[0],split2[0]);
				predicates.add(between);
				default:
					throw new IllegalStateException("unexpected value");
				}
				
			}
			
			if(globalOperator.equals(SpecificRequestDto.GlobalOperator.AND)) {
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}else {
				return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
			}
			
		};
		
	}
}
