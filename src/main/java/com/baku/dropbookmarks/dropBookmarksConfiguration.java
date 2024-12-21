package com.baku.dropbookmarks;

import com.baku.dropbookmarks.job.QuartzJobConfig;
import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import jakarta.validation.constraints.*;

import java.util.Map;

@Data
public class dropBookmarksConfiguration extends Configuration {
    // TODO: implement service configuration
    @JsonProperty("country")
    private String countryName;

    private String myAppName;

    private int defaultCountValue;

    private Map<String, String> countryConfig;

    @NotNull
    @JsonProperty
    @Valid
    private QuartzJobConfig quartzJobConfig;
}
