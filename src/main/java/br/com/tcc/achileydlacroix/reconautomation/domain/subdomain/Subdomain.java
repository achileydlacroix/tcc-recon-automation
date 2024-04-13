package br.com.tcc.achileydlacroix.reconautomation.domain.subdomain;

import br.com.tcc.achileydlacroix.reconautomation.domain.Domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

@Entity
@Table(name = "subdomains")
public class Subdomain {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotBlank
    private String url;
    @NotNull
    @ManyToOne
    private Domain domain;
//    @OneToMany(mappedBy = "domain",cascade = CascadeType.MERGE)
//    @OrderBy("firstSeen asc")
//    private SortedSet<Vulnerability> vulnerabilities = new TreeSet<>();

    @Deprecated
    public Subdomain() {
    }

    public Subdomain(@NotBlank String url) {
        Assert.hasLength(url, "O campo url nao pode estar em branco");

        this.url = url;
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

    public String getUrl() {
        return url;
    }


    public Domain getHost() {
        return domain;
    }

//    public SortedSet<Vulnerability> getVulnerabilities() {
//        return vulnerabilities;
//    }
//
//    public void addVulnerabilities(@NotNull Vulnerability vulns){
//        Assert.notNull(vulns, "O objeto vulnerability nao pode estar nulo");
//        this.vulnerabilities.add(vulns);
//    }
}
