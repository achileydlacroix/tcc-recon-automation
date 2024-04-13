package br.com.tcc.achileydlacroix.reconautomation.domain;

import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.Subdomain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "domain")
public class Domain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    @Size(max = 150)
    private String url;
    @NotBlank
    @OneToMany(mappedBy = "domain", cascade = CascadeType.MERGE)
    private Set<Subdomain> subdomains = new HashSet<>();
//    @OneToMany(mappedBy = "domain",cascade = CascadeType.MERGE)
//    @OrderBy("firstSeen asc")
//    private SortedSet<Vulnerability> vulnerabilities = new TreeSet<>();

    @Deprecated
    public Domain() {
    }

    public Domain(@NotBlank String url) {
        Assert.hasLength(url, "O campo url nao pode estar em branco");

        this.url = url;
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

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }


    public Set<Subdomain> getSubdomains() {
        return subdomains;
    }

//    public SortedSet<Vulnerability> getVulnerabilities() {
//        return vulnerabilities;
//    }
//
//    public void addVulnerabilities(@NotNull Vulnerability vulns){
//        Assert.notNull(vulns, "O objeto vulnerability nao pode estar nulo");
//        this.vulnerabilities.add(vulns);
//    }

    public void setSubdomains(Set<Subdomain> subdomains) {
        this.subdomains = subdomains;
    }
}

