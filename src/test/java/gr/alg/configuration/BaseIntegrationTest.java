package gr.alg.configuration;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@QuarkusTest
public class BaseIntegrationTest {

  private static final ConfiguredPostgresContainer postgres;

  static {
    postgres = ConfiguredPostgresContainer.getInstance();
    postgres.start();
  }
}