package org.silsagusi.api.inquiry.infrastructure.dataprovider;

import java.util.List;

import org.silsagusi.api.exception.CustomException;
import org.silsagusi.api.exception.ErrorCode;
import org.silsagusi.api.inquiry.infrastructure.repository.InquiryAnswerRepository;
import org.silsagusi.api.inquiry.infrastructure.repository.InquiryRepository;
import org.silsagusi.core.domain.inquiry.command.UpdateInquiryCommand;
import org.silsagusi.core.domain.inquiry.entity.Inquiry;
import org.silsagusi.core.domain.inquiry.entity.InquiryAnswer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InquiryDataProvider {

	private final InquiryRepository inquiryRepository;
	private final InquiryAnswerRepository inquiryAnswerRepository;

	public void createInquiry(Inquiry inquiry) {
		inquiryRepository.save(inquiry);
	}

	public void updateInquiry(Inquiry inquiry, UpdateInquiryCommand updateInquiryCommand) {
		inquiry.updateInquiry(updateInquiryCommand.getName(), updateInquiryCommand.getTitle(),
			updateInquiryCommand.getContent());
	}

	public void deleteInquiry(Inquiry inquiry) {
		inquiry.markAsDeleted();
	}

	public Inquiry getInquiry(Long inquiryId) {
		return inquiryRepository.findByIdAndDeletedAtIsNull(inquiryId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT));
	}

	public List<Inquiry> getInquiries() {
		return inquiryRepository.findAllByDeletedAtIsNull();
	}

	public void createInquiryAnswer(InquiryAnswer inquiryAnswer) {
		inquiryAnswerRepository.save(inquiryAnswer);
	}

	public void markAsAnswered(Inquiry inquiry) {
		inquiry.markAsAnswered();
	}
}
