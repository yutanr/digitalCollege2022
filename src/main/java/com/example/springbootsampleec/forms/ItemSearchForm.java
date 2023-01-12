package com.example.springbootsampleec.forms;

import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ItemSearchForm {
	@Size(min=1, max=20)
	private String name;
	
    @Size(min=1, max=20)
	private String description;
}
