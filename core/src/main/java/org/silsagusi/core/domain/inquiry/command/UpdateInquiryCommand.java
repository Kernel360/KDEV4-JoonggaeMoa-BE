package org.silsagusi.core.domain.inquiry.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateInquiryCommand {

	private String title;

	private String content;
}
