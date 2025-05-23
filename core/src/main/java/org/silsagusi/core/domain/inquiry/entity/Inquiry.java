package org.silsagusi.core.domain.inquiry.entity;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.silsagusi.core.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "inquiries")
public class Inquiry extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inquiry_id", nullable = false)
	private Long id;

	private String name;

	private String password;

	private String title;

	@Lob
	private String content;

	@Column(name = "answer_count")
	private Integer answerCount;

	@Column(name = "is_answered")
	private boolean isAnswered;

	@OneToMany(mappedBy = "inquiry", fetch = FetchType.LAZY)
	private List<InquiryAnswer> answers = new ArrayList<>();

	private Inquiry(String name, String password, String title, String content) {
		this.name = name;
		this.password = password;
		this.title = title;
		this.content = content;
		this.answerCount = 0;
	}

	public static Inquiry create(String name, String password, String title, String content) {
		return new Inquiry(name, password, title, content);
	}

	public void updateInquiry(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void markAsAnswered() {
		this.isAnswered = true;
	}

	public void increaseAnswerCount() {
		this.answerCount++;
	}

}
