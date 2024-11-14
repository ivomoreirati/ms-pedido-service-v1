package br.com.k2.rci.adapters.saida.repository;


import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("jpa-integration")
public class JpaTestBase {
}
