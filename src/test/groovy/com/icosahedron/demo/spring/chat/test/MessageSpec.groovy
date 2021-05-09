package com.icosahedron.demo.spring.chat.test

import com.icosahedron.demo.spring.chat.repository.ContentType
import com.icosahedron.demo.spring.chat.repository.Message
import spock.lang.Specification

import java.time.Instant

final class MessageSpec extends Specification {
    def "testing"() {
        given:
        def content = '*testMessage1*'
        def userName = 'test1'
        def avatarLink = 'http://test.com'
        def message = new Message(content, ContentType.PLAIN, Instant.now(), userName, avatarLink)

        expect:
        println message
    }
}
