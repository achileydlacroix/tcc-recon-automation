package br.com.tcc.achileydlacroix.reconautomation.domain;

import jakarta.validation.constraints.NotBlank;

public class NewDomainRequest {
    @NotBlank
    private String url;

    @Deprecated
    public NewDomainRequest() {
    }

    public NewDomainRequest(@NotBlank String url) {
        super();
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
