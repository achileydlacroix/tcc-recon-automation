package br.com.tcc.achileydlacroix.reconautomation.domain.subdomain;

import br.com.tcc.achileydlacroix.reconautomation.vulnerability.Vulnerability;
import br.com.tcc.achileydlacroix.reconautomation.vulnerability.VulnerabilityResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubdomainResponse {

    private Long id;
    private String address;
    private List<VulnerabilityResponse> vulnerabilities;

    public SubdomainResponse(Subdomain subdomain) {

        this.id = subdomain.getId();
        this.address = subdomain.getAddress();
        this.vulnerabilities = subdomain.getVulnerabilities().stream().map(VulnerabilityResponse::new).collect(Collectors.toList());;
    }

    public Long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public List<VulnerabilityResponse> getVulnerabilities() {
        return vulnerabilities;
    }
}
