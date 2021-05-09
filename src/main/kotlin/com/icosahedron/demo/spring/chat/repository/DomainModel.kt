package com.icosahedron.demo.spring.chat.repository

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("MESSAGES")
data class Message @JvmOverloads constructor(
    val content: String,
    val contentType: ContentType,
    val sent: Instant,
    val username: String,
    val userAvatarImageLink: String,
    @Id var id: String? = null)

enum class ContentType {
    PLAIN
}