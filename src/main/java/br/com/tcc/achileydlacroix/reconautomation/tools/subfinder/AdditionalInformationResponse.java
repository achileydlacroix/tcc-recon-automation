package br.com.tcc.achileydlacroix.reconautomation.tools.subfinder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AdditionalInformationResponse {
    @JsonProperty("total_subdomains")
    private int totalSubdomains;
    @JsonProperty("total_ip_addresses")
    private int totalIpAddresses;
    @JsonProperty("scan_date")
    private String scanDate;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AdditionalInformationResponse(int totalSubdomains, int totalIpAddresses, String scanDate) {
        this.totalSubdomains = totalSubdomains;
        this.totalIpAddresses = totalIpAddresses;
        this.scanDate = scanDate;
    }
}
