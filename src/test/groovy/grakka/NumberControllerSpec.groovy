package grakka

import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(NumberController)
class NumberControllerSpec extends Specification {

    @Shared requestIds = [].toSet()

    void "test search has a unique id"() {

        when:
        request.method = 'GET'
        controller.random()

        then:
        requestIds instanceof Set
        def requestId = response.json.id
        response.json.id != null
        requestIds << requestId
        requestIds.size() == i

        where:
        i << (1..1000)

    }
}
