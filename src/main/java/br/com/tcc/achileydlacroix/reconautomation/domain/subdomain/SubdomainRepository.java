package br.com.tcc.achileydlacroix.reconautomation.domain.subdomain;

import br.com.tcc.achileydlacroix.reconautomation.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubdomainRepository extends JpaRepository<Subdomain, Long> {
    Optional<Subdomain> findByAddress(String address);
}
