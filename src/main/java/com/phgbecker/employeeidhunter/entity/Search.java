package com.phgbecker.employeeidhunter.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Search {
	private String searchItem;
	private int maxItems;

	public Search(String searchItem, int maxItems) {
		this.searchItem = searchItem;
		this.maxItems = maxItems;
	}

}
