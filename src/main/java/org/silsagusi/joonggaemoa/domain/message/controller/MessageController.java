package org.silsagusi.joonggaemoa.domain.message.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.MessageDto;
import org.silsagusi.joonggaemoa.domain.message.controller.dto.ReservedMessageDto;
import org.silsagusi.joonggaemoa.domain.message.service.MessageService;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;
import org.silsagusi.joonggaemoa.global.api.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/api/messages")
    public ResponseEntity<ApiResponse<Page<MessageDto.Response>>> getMessage(
            HttpServletRequest request,
            Pageable pageable
            //@RequestParam(required = false) Long lastMessageId
    ) {
        Page<MessageCommand> messageCommandPage = messageService.getMessage(
                (Long) request.getAttribute("agentId"), pageable
        );

        Page<MessageDto.Response> responses = messageCommandPage.map(MessageDto.Response::of);

        return ResponseEntity.ok(ApiResponse.ok(responses));
    }


    @PostMapping("/api/messages")
    public ResponseEntity<ApiResponse<Void>> reserveMessage(
            @RequestBody @Valid ReservedMessageDto.Request request
    ) {
        messageService.reserveMessage(request.getContent(), request.getSendAt(), request.getCustomerIdList());
        return ResponseEntity.ok(ApiResponse.ok());
    }

    @GetMapping("/api/reserved-message")
    public ResponseEntity<ApiResponse<Page<ReservedMessageDto.Response>>> getReservedMessage(
            HttpServletRequest request,
            Pageable pageable
    ) {

        Page<ReservedMessageCommand> commandPage = messageService.getReservedMessage(
                (Long) request.getAttribute("agentId"),
                pageable
        );

        Page<ReservedMessageDto.Response> responsePage = commandPage
                .map(ReservedMessageDto.Response::of);

        return ResponseEntity.ok(ApiResponse.ok(responsePage));
    }
}
