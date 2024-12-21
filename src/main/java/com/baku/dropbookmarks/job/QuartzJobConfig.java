package com.baku.dropbookmarks.job;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuartzJobConfig {

    @NotNull
    @Valid
    String threadPoolSize;
}
