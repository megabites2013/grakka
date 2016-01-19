package grakka

import geb.spock.GebSpec
import grails.test.mixin.integration.Integration
import groovy.json.JsonSlurper
import spock.lang.Shared

@Integration
class NumberControllerSpec extends GebSpec {

  @Shared jsonSlurper = new JsonSlurper()

  @Shared requestIds = [].toSet()
  @Shared randomIntegers = [].toSet()

  void "test request for random number has a unique id and a reasonably-random Integer"() {

      when:
      def response = new URL("http://localhost:8080/number/random").getText()

      then:
      def responseJson = jsonSlurper.parseText(response)

      responseJson.id != null
      requestIds << responseJson.id
      requestIds.size() == i

      def randomInteger = responseJson.randomInteger
      randomInteger != null
      randomInteger >= 0
      randomIntegers << randomInteger
      randomIntegers.size() >= (i - 1) // allow for one repeated random number, at most

      where:
      i << (1..10)

  }

}
