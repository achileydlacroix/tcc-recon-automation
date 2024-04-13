package br.com.tcc.achileydlacroix.reconautomation.domain.subdomain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubdomainRepository extends JpaRepository<Subdomain, Long> {
}
