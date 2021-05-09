package com.icosahedron.demo.spring.chat.service

import com.icosahedron.demo.spring.chat.asDomainObject
import com.icosahedron.demo.spring.chat.asViewModel
import com.icosahedron.demo.spring.chat.mapToViewModel
import com.icosahedron.demo.spring.chat.repository.Message
import com.icosahedron.demo.spring.chat.repository.MessageRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {
    override fun latest(): List<MessageVM> =
        messageRepository.findLatest().mapToViewModel()

    override fun after(lastMessageId: String): List<MessageVM> =
        messageRepository.findLatest(lastMessageId).mapToViewModel()

    override fun post(message: MessageVM) {
        messageRepository.save(message.asDomainObject())
    }
}