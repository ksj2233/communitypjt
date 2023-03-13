package com.keduit.helloworld.handler;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageRequestUtil {

    public PageRequest getPageRequest(int page, int size, String sort) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.contains(",")) {
            String[] sortInfo = sort.split(",");
            sort = sortInfo[0];
            direction = sortInfo[1].equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, sort));
    }

    public String getSortQueryString(String sort) {
        if (sort.contains(",")) {
            String[] sortInfo = sort.split(",");
            return String.format("&sort=%s,%s", sortInfo[0], sortInfo[1].equalsIgnoreCase("asc") ? "desc" : "asc");
        } else {
            return String.format("&sort=%s,desc", sort);
        }
    }
}