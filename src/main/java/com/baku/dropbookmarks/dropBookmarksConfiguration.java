package com.baku.dropbookmarks;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import jakarta.validation.constraints.*;
@Data
public class dropBookmarksConfiguration extends Configuration {
    // TODO: implement service configuration
    @JsonProperty("country")
    private String countryName;
}
