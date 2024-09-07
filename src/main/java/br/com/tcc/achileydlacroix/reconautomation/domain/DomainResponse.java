package br.com.tcc.achileydlacroix.reconautomation.domain;

import br.com.tcc.achileydlacroix.reconautomation.domain.subdomain.SubdomainResponse;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.VulnerabilityResponse;

import java.util.List;
import java.util.stream.Collectors;

public class DomainResponse {
    private Long id;
    private String address;
    private List<SubdomainResponse> subdomains;
    private List<VulnerabilityResponse> vulnerabilities;

    public DomainResponse(Domain domain) {
        this.id = domain.getId();
        this.address = domain.getAddress();
        this.subdomains = domain.getSubdomains().stream().map(SubdomainResponse::new).collect(Collectors.toList());
        this.vulnerabilities = domain.getVulnerabilities().stream().map(VulnerabilityResponse::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public List<SubdomainResponse> getSubdomains() {
        return subdomains;
    }

    public List<VulnerabilityResponse> getVulnerabilities() {
        return vulnerabilities;
    }

    public static List<DomainResponse> convert(List<Domain> domains) {
        return domains.stream().map(DomainResponse::new).collect(Collectors.toList());
    }
}
