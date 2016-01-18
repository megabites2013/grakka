package grakka

import grails.test.mixin.TestFor
import spock.lang.Shared
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(NumberController)
class NumberControllerSpec extends Specification {

    @Shared searchIds = [].toSet()

    void "test search has a unique id"() {

        when:
        request.method = 'GET'
        controller.index()

        then:
        searchIds instanceof Set
        def searchId = response.json.searchId
        response.json.searchId != null
        searchIds << searchId
        searchIds.size() == i

        where:
        i << (1..1000)

    }
}
