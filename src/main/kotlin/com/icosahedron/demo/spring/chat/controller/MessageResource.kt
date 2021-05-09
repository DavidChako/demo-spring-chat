package com.icosahedron.demo.spring.chat.controller

import com.icosahedron.demo.spring.chat.service.MessageService
import com.icosahedron.demo.spring.chat.service.MessageVM
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/messages")
class MessageResource(val messageService: MessageService) {
    @GetMapping
    fun latest(@RequestParam(value = "lastMessageId", defaultValue = "") lastMessageId: String):
            ResponseEntity<List<MessageVM>> {
        val messages = if (lastMessageId.isNotEmpty()) {
            messageService.after(lastMessageId)
        } else {
            messageService.latest()
        }

        return if (messages.isEmpty()) {
            with(ResponseEntity.noContent()) {
                header("lastMessageId", lastMessageId)
                build()
            }
        } else {
            with (ResponseEntity.ok()) {
                header("lastMessageId", messages.last().id)
                body(messages)
            }
        }
    }

    @PostMapping
    fun post(@RequestBody message: MessageVM) {
        messageService.post(message)
    }
}