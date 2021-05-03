package com.icosahedron.demo.spring.chat.test

import com.icosahedron.demo.spring.chat.controller.HtmlController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
final class DemoSpringChatAppSpec extends Specification {
    @Autowired HtmlController htmlController

    def "testing"() {
        expect:
        println "hello"

        and:
        htmlController

        and:
        println htmlController.properties
    }
}
