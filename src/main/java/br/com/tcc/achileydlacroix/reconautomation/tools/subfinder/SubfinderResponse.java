package br.com.tcc.achileydlacroix.reconautomation.tools.subfinder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubfinderResponse {
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("subdomains")
    private List<String> subdomains;
    @JsonProperty("ip_addresses")
    private Map<String, List<String>> ipAddresses = new HashMap<>();
    @JsonProperty("additional_info")
    private AdditionalInformationResponse additionalInfo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SubfinderResponse(String domain, List<String> subdomains, Map<String, List<String>> ipAddresses, AdditionalInformationResponse additionalInfo) {
        this.domain = domain;
        this.subdomains = subdomains;
        this.ipAddresses = ipAddresses;
        this.additionalInfo = additionalInfo;
    }

    public List<String> getSubdomains() {
        return subdomains;
    }
}
