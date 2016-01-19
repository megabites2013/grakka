package grakka.number

import javax.inject.Named

/**
 * NumberService provides fun number-related services.
 */
@Named("NumberService")
class NumberService {

  final Random random = new Random()

  /**
   * Generates a random integer within the range of 0 (inclusive) up to the specified maximum (exclusive).
   *
   * @param max the upper bound (exclusive).  Must be positive.
   * @return a list of nextInt results
   */
  int nextInt(int max) {
      return random.nextInt(max)
    }
}
