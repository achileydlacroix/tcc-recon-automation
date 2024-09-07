package br.com.tcc.achileydlacroix.reconautomation.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class NewDomainRequest {
    @NotBlank
    @Pattern(regexp = "^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$")
    private String address;

    @Deprecated
    public NewDomainRequest() {
    }

    public NewDomainRequest(@NotBlank String address) {
        super();
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
