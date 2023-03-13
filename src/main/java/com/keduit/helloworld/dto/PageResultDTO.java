package com.keduit.helloworld.dto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ch.qos.logback.core.net.SyslogOutputStream;
import lombok.Data;

@Data
public class PageResultDTO<DTO, EN> {

	private List<DTO> dtoList;
	private int totalPage;
	private int page;	
	private int size;
	private int start;
	private int end;
	private boolean prev, next;
	private List<Integer> pageList;
	
	public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
		dtoList = result.stream().map(fn).collect(Collectors.toList());
		totalPage = result.getTotalPages();
		makePageList(result.getPageable());
	}

	private void makePageList(Pageable pageable) {
		this.page = pageable.getPageNumber() + 1;
		this.size = pageable.getPageSize() + 1;
		
		int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

		start = tempEnd -9;
		
		System.out.println("start--" + start);
		prev = start > 1;
		next = totalPage > tempEnd;
		end = totalPage > tempEnd ? tempEnd + 1 : totalPage + 1;
		pageList = IntStream.range(start, end).boxed().collect(Collectors.toList());
		
	}
	
}
