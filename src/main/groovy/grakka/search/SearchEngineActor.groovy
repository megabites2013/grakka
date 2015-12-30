package grakka.search

import akka.actor.UntypedActor
import akka.dispatch.Futures
import akka.pattern.Patterns
import groovy.transform.Immutable
import org.springframework.context.annotation.Scope
import scala.concurrent.Future

import javax.inject.Inject
import javax.inject.Named
import java.util.concurrent.Callable

/**
 * SearchEngineActor is responsible for interacting with the search engine.
 */
@Named("SearchEngineActor")
@Scope("prototype")
class SearchEngineActor extends UntypedActor {

  @Immutable
  public static class PerformQuery {
    String id
    String criteria
  }

  // the service that will be automatically injected
  final SearchEngineService searchEngine;

  @Inject
  public SearchEngineActor(@Named("SearchEngineService") SearchEngineService searchEngine) {
    this.searchEngine = searchEngine;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof PerformQuery) {
      PerformQuery query = message
      Callable<List<String>> search = new Callable<List<String>>() {
        @Override
        List<String> call() throws Exception {
          return searchEngine.search(query.criteria)
        }
      }
      Future<List<String>> futureSearchResults = Futures.future(search, this.context.dispatcher())
      Patterns.pipe(futureSearchResults, context().dispatcher()).to(sender, self)
    } else {
      unhandled(message);
    }
  }

}