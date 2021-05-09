package com.icosahedron.demo.spring.chat.test

import com.icosahedron.demo.spring.chat.repository.ContentType
import com.icosahedron.demo.spring.chat.repository.Message
import com.icosahedron.demo.spring.chat.repository.MessageRepository
import com.icosahedron.demo.spring.chat.service.MessageVM
import com.icosahedron.demo.spring.chat.service.UserVM
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = [ '"spring.r2dbc.url=r2dbc:h2:mem:///testdb;USER=sa;PASSWORD=password"' ]
)
final class DemoSpringChatAppSpec extends Specification {
    @Autowired TestRestTemplate client
    @Autowired MessageRepository messageRepository

    @Shared initialMessageCount = 10
    def random = new Random()

    def setup() {
        (initialMessageCount-1..0).each {offset ->
            def contentType = random.nextBoolean() ? ContentType.MARKDOWN : ContentType.PLAIN
            def content = '*testMessage' + offset + '*'
            def username = 'test' + offset
            def sent = Instant.now().minusSeconds(offset)
            def avatarLink = 'http://test.com'
            def message = new Message(content, contentType, sent, username, avatarLink)
            messageRepository.save(message)
        }
    }

    def cleanup() {
        messageRepository.deleteAll()
    }

    def "latest messages"() {
        given:
        def savedMessages = messageRepository.findAll()
        def uri = new URI('/api/v1/messages?lastMessageId=')
        def entity = new RequestEntity(HttpMethod.GET, uri)
        def typeRef = new ParameterizedTypeReference<List<MessageVM>>() {}

        def expectedMessages = []
        savedMessages.each {message ->
            def avatarUrl = new URL(message.userAvatarImageLink)
            def userVM = new UserVM(message.username, avatarUrl)

            def content = message.contentType == ContentType.PLAIN ? message.content :
                    '<body><p><em>' + message.content.replaceAll('\\*','') + '</em></p></body>'

            def messageVM = new MessageVM(content, userVM, message.sent, message.id)
            expectedMessages.add(messageVM)
        }

        when:
        def messages = client.exchange(entity, typeRef).body

        then:
        messages == expectedMessages

        and:
        println messages.join('\n')
    }

    @Unroll
    def "latest messages after last message id offset=#lastMessageOffset"() {
        given:
        def savedMessages = messageRepository.findAll()
        def lastMessage = savedMessages[lastMessageOffset]
        def uri = new URI('/api/v1/messages?lastMessageId=' + lastMessage.id)
        def entity = new RequestEntity(HttpMethod.GET, uri)
        def typeRef = new ParameterizedTypeReference<List<MessageVM>>() {}

        def expectedMessages = []
        savedMessages.each {message ->
            if (message.sent > lastMessage.sent) {
                def avatarUrl = new URL(message.userAvatarImageLink)
                def userVM = new UserVM(message.username, avatarUrl)

                def content = message.contentType == ContentType.PLAIN ? message.content :
                        '<body><p><em>' + message.content.replaceAll('\\*','') + '</em></p></body>'

                def messageVM = new MessageVM(content, userVM, message.sent, message.id)
                expectedMessages.add(messageVM)
            }
        }

        when:
        def messages = client.exchange(entity, typeRef).body

        then:
        messages == expectedMessages

        where:
        lastMessageOffset << (0..initialMessageCount-2).collect { it }
    }
}
