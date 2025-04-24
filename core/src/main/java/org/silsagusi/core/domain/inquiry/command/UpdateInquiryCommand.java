package org.silsagusi.core.domain.inquiry.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateInquiryCommand {
	private String name;
	
	private String password;

	private String title;

	private String content;
}
