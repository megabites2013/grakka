package grakka.search

import akka.actor.UntypedActor
import org.springframework.context.annotation.Scope

import javax.inject.Inject
import javax.inject.Named

/**
 * SearchEngineActor is responsible for interacting with the search engine.
 */
@Named("SearchEngineActor")
@Scope("prototype")
class SearchEngineActor extends UntypedActor {

  public static class PerformQuery {
    private final String id
    private final String criteria
    PerformQuery(String id, String criteria){
      this.id = id
      this.criteria = criteria
    }
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