package br.com.tcc.achileydlacroix.reconautomation.scanners.subfinder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SubfinderResponse {


    private String host;
    private String input;
    private String source;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public SubfinderResponse(@JsonProperty("host") String host,
                             @JsonProperty("input") String input,
                             @JsonProperty("source") String source) {
        this.host = host;
        this.input = input;
        this.source = source;
    }

    public String getHost() {
        return host;
    }
}
