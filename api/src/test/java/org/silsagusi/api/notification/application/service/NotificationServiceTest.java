package org.silsagusi.api.notification.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.silsagusi.api.notification.infrastructure.dataprovider.NotificationDataProvider;
import org.silsagusi.core.domain.notification.entity.NotificationType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

	@InjectMocks
	private NotificationService notificationService;

	@Mock
	private NotificationDataProvider notificationDataProvider;

	@Test
	void 클라이언트_구독_성공_확인() {
		// Given
		Long agentId = 1L;
		String clientId = "client1";
		SseEmitter mockEmitter = new SseEmitter();
		when(notificationDataProvider.subscribe(agentId, clientId)).thenReturn(mockEmitter);

		// When
		SseEmitter emitter = notificationService.subscribe(agentId, clientId);

		// Then
		assertThat(emitter).isEqualTo(mockEmitter);
		verify(notificationDataProvider, times(1)).subscribe(agentId, clientId);
	}

	@Test
	void 여러_클라이언트가_구독_성공_확인() {
		// Given
		Long agentId = 1L;
		String clientId1 = "client1";
		String clientId2 = "client2";
		SseEmitter mockEmitter1 = new SseEmitter();
		SseEmitter mockEmitter2 = new SseEmitter();
		when(notificationDataProvider.subscribe(agentId, clientId1)).thenReturn(mockEmitter1);
		when(notificationDataProvider.subscribe(agentId, clientId2)).thenReturn(mockEmitter2);

		// When
		SseEmitter emitter1 = notificationService.subscribe(agentId, clientId1);
		SseEmitter emitter2 = notificationService.subscribe(agentId, clientId2);

		// Then
		assertThat(emitter1).isEqualTo(mockEmitter1);
		assertThat(emitter2).isEqualTo(mockEmitter2);
		verify(notificationDataProvider, times(1)).subscribe(agentId, clientId1);
		verify(notificationDataProvider, times(1)).subscribe(agentId, clientId2);
	}

	@Test
	void 알림_전송_성공_확인() throws Exception {
		// Given
		Long agentId = 1L;
		NotificationType type = NotificationType.MESSAGE;
		String content = "test notification";

		// When
		notificationDataProvider.notify(agentId, type, content);

		// Then
		verify(notificationDataProvider, times(1)).notify(agentId, type, content);
	}
}
