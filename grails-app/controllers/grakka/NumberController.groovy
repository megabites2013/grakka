package grakka

import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import akka.util.Timeout
import grails.async.Promises
import grails.core.GrailsApplication
import grakka.search.SearchEngineActor
import scala.concurrent.Await
import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

import java.util.concurrent.TimeUnit

import static akka.pattern.Patterns.ask
import static conf.SpringExtension.SpringExtProvider

class NumberController {

    static final FiniteDuration DURATION_3_SECONDS = FiniteDuration.create(3, TimeUnit.SECONDS)
    static final TIMEOUT_3_SECONDS = Timeout.durationToTimeout(DURATION_3_SECONDS)

    ActorSystem actorSystem
    GrailsApplication grailsApplication

    def random() {
//        some print statements to help debug things
//        println("actorSystem: ${actorSystem}")
//        println("beans: ${grailsApplication.mainContext.beanDefinitionNames}")

        def searchId = UUID.randomUUID().toString()

        // describe the properties of the desired actor
        def actorType = "SearchEngineActor"
        Props props = SpringExtProvider.get(actorSystem).props(actorType)

        // ask akka to create the actor
        // use unique actor name because this will be an ephemeral, stateless actor
        // managed as a prototype bean in the spring context; great for crashy-stuff like search
        def actorName = "${actorType}-${searchId}"
        ActorRef searchEngineRef = actorSystem.actorOf(props, actorName)

        def query = new SearchEngineActor.PerformQuery(searchId, "some query criteria")
        Future<Object> futureResults = ask(searchEngineRef, query, TIMEOUT_3_SECONDS)

        Promises.task {
            def searchResults = Await.result(futureResults, DURATION_3_SECONDS)
            println("received: " + searchResults)

            actorSystem.stop(searchEngineRef)

            render(contentType: 'application/json') {
                id = searchId
                results = searchResults
            }
        }
    }
}
