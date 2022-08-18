package com.nextfeed.library.core.service.entities;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	private String registrationNumber;
	private String name;
	private String grade;
}