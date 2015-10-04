package grakka.search

import javax.inject.Named

/**
 * SearchEngineService is responsible for producing some search results.
 */
@Named("SearchEngineService")
class SearchEngineService {

  final Random random = new Random()

  /**
   * Uses the provided query criteria to perform a search.
   *
   * @return a list of search results
   */
  List<String> search(String query) {
      println "searching with criteria: ${query}"
      sleep(100)
      return ["a random search result: ${Math.abs(random.nextInt(100000))}"]
    }
}
