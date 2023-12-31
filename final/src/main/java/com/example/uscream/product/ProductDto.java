package com.example.uscream.product;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
	private int productnum;
	private String productname;
	private String productimg;
	private String productinfo;
	private String productcategory;
	private int cost;
	private boolean orderble;
	private MultipartFile pimg;
}
