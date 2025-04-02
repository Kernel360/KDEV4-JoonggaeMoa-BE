package org.silsagusi.joonggaemoa.domain.message.service;

import lombok.RequiredArgsConstructor;
import org.silsagusi.joonggaemoa.domain.customer.repository.CustomerRepository;
import org.silsagusi.joonggaemoa.domain.message.entity.Message;
import org.silsagusi.joonggaemoa.domain.message.entity.ReservedMessage;
import org.silsagusi.joonggaemoa.domain.message.repository.MessageRepository;
import org.silsagusi.joonggaemoa.domain.message.repository.ReservedMessageRepository;
import org.silsagusi.joonggaemoa.domain.message.service.command.MessageCommand;
import org.silsagusi.joonggaemoa.domain.message.service.command.ReservedMessageCommand;
import org.silsagusi.joonggaemoa.global.api.exception.CustomException;
import org.silsagusi.joonggaemoa.global.api.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final CustomerRepository customerRepository;
    private final MessageRepository messageRepository;
    private final ReservedMessageRepository reservedMessageRepository;

    public Page<MessageCommand> getMessage(Long agentId, Pageable pageable) {
        List<Message> messages;

//        if (lastMessageId == null || lastMessageId == 0) {
//            // 초기 요청: 가장 최신 메시지 10개 조회
//            messages = messageRepository.findTop10ByCustomerAgent_IdOrderByIdDesc(agentId);
//        } else {
//            // lastMessageId 이후 메시지 10개 조회
//            messages = messageRepository.findTop10ByCustomerAgent_IdAndIdLessThanOrderByIdDesc(agentId, lastMessageId);
//        }
        Page<Message> messagePage = messageRepository.findAll(pageable);

        return messagePage.map(MessageCommand::of);
    }

    public void reserveMessage(String content, String sendAt, List<Long> customerIdList) {
        customerIdList.stream()
                .map(id -> customerRepository.findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ELEMENT)))
                .map(customer -> new ReservedMessage(customer, LocalDateTime.parse(sendAt), content))
                .forEach(reservedMessageRepository::save);
    }

    public Page<ReservedMessageCommand> getReservedMessage(Long agentId, Pageable pageable) {
        Page<ReservedMessage> reservedMessagePage = reservedMessageRepository.findAll(pageable);

//        if (lastMessageId == null || lastMessageId == 0) {
//            reservedMessages = reservedMessageRepository.findTop10ByCustomerAgent_IdOrderByIdDesc(agentId);
//        } else {
//            reservedMessages = reservedMessageRepository.findTop10ByCustomerAgent_IdAndIdLessThanOrderByIdDesc(agentId,
//                    lastMessageId);
//        }


        return reservedMessagePage.map(ReservedMessageCommand::of);
    }

}
