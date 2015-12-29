package grakka.search

import akka.actor.UntypedActor
import groovy.transform.Immutable
import org.springframework.context.annotation.Scope

import javax.inject.Inject
import javax.inject.Named

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
      getSender().tell(searchEngine.search(query.criteria), getSelf());
    } else {
      unhandled(message);
    }
  }

}