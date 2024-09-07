package br.com.tcc.achileydlacroix.reconautomation.domain;

import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.Subdomain;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.Vulnerability;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.util.Assert;

import java.util.*;

@Entity
@Table(name = "domains")
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 150)
    @Pattern(regexp = "^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$")
    private String address;
    @OneToMany(mappedBy = "domain", cascade = CascadeType.REMOVE)
    private Set<Subdomain> subdomains = new HashSet<>();
    @OneToMany(mappedBy = "domain",cascade = CascadeType.REMOVE)
    private Set<Vulnerability> vulnerabilities = new HashSet<>();

    @Deprecated
    public Domain() {
    }

    public Domain(@NotBlank String address) {
        Assert.hasLength(address, "O campo address nao pode estar em branco");

        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Domain domain = (Domain) o;
        return Objects.equals(id, domain.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public Set<Subdomain> getSubdomains() {
        return subdomains;
    }

    public Set<Vulnerability> getVulnerabilities() {

        return vulnerabilities;
    }

    public void addVulnerabilities(@NotNull Vulnerability vulnerability){
        Assert.notNull(vulnerability, "O objeto vulnerability nao pode estar nulo");
        this.vulnerabilities.add(vulnerability);
    }

    public void addSubdomains(Subdomain subdomain) {
        Assert.notNull(subdomain, "O objeto subdomain nao pode estar nulo");
        this.subdomains.add(subdomain);
    }
}

