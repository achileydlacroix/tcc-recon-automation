package br.com.tcc.achileydlacroix.reconautomation.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByUrl(String url);

}
