package com.icosahedron.demo.spring.chat.service

interface MessageService {
    fun latest() : List<MessageVM>
    fun after(lastMessageId: String): List<MessageVM>
    fun post(message: MessageVM)
}