package grakka.number

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
 * SearchEngineActor is responsible for interacting with the nextInt engine.
 */
@Named("NumberActor")
@Scope("prototype")
class NumberActor extends UntypedActor {

  @Immutable
  public static class GetRandomInteger {
    String id
    Integer max
  }

  // the service that will be automatically injected
  final NumberService numberService;

  @Inject
  public NumberActor(@Named("NumberService") NumberService numberService) {
    this.numberService = numberService;
  }

  @Override
  public void onReceive(Object message) throws Exception {
    if (message instanceof GetRandomInteger) {
      GetRandomInteger query = message
      Callable<Integer> op = new Callable<Integer>() {
        @Override
        Integer call() throws Exception {
          return numberService.nextInt(query.max)
        }
      }
      Future<Integer> futureOpResults = Futures.future(op, this.context.dispatcher())
      Patterns.pipe(futureOpResults, context().dispatcher()).to(sender, self)
    } else {
      unhandled(message);
    }
  }

}