package br.com.tcc.achileydlacroix.reconautomation.domain.subdomain;

import br.com.tcc.achileydlacroix.reconautomation.domain.Domain;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.Vulnerability;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.*;

@Entity
@Table(name = "subdomains")
public class Subdomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String address;
    @NotNull
    @ManyToOne
    private Domain domain;
    @OneToMany(mappedBy = "subdomain",cascade = CascadeType.REMOVE)
    private Set<Vulnerability> vulnerabilities = new HashSet<>();

    @Deprecated
    public Subdomain() {
    }

    public Subdomain(@NotBlank String address, @NotNull Domain domain) {
        Assert.hasLength(address, "O campo address nao pode estar em branco");
        Assert.notNull(domain, "O dominio nao pode ser nulo");

        this.address = address;
        this.domain = domain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subdomain subdomain = (Subdomain) o;
        return Objects.equals(id, subdomain.id);
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


    public Set<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }

    public void addVulnerabilities(@NotNull Vulnerability vulns){
        Assert.notNull(vulns, "O objeto vulnerability nao pode estar nulo");
        this.vulnerabilities.add(vulns);
    }
}
