package conf

import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import static conf.SpringExtension.SpringExtProvider

/**
 * AkkaSpringConfiguration is responsible for 
 */
@Configuration
class AkkaSpringConfiguration {
  //mostly-from https://raw.githubusercontent.com/typesafehub/activator-akka-java-spring/master/src/main/java/sample/AppConfiguration.java

  // the application context is needed to initialize the Akka Spring Extension
  @Autowired
  private ApplicationContext applicationContext;

  /**
   * Actor system singleton for this application.
   */
  @Bean
  public ActorSystem actorSystem() {
    ActorSystem system = ActorSystem.create("actor-system");
    // initialize the application context in the Akka Spring Extension
    SpringExtProvider.get(system).initialize(applicationContext);
    return system;
  }

  /**
   * Read configuration from application.conf file
   */
  @Bean
  public Config akkaConfiguration() {
    return ConfigFactory.load("actor-system");
  }

}
