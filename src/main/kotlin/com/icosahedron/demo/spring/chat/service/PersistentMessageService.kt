package com.icosahedron.demo.spring.chat.service

import com.icosahedron.demo.spring.chat.repository.ContentType
import com.icosahedron.demo.spring.chat.repository.Message
import com.icosahedron.demo.spring.chat.repository.MessageRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.net.URL

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {
    override fun latest(): List<MessageVM> =
        messageRepository.findLatest()
            .map { with(it) { MessageVM(content, UserVM(username,
                URL(userAvatarImageLink)), sent, id) } }

    override fun after(lastMessageId: String): List<MessageVM> =
        messageRepository.findLatest(lastMessageId)
            .map { with(it) { MessageVM(content, UserVM(username,
                URL(userAvatarImageLink)), sent, id) } }

    override fun post(message: MessageVM) {
        messageRepository.save(
            with(message) { Message(content, ContentType.PLAIN, sent,
                user.name, user.avatarImageLink.toString()) }
        )
    }
}