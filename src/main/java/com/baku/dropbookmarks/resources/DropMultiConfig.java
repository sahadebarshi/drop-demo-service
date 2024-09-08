package com.baku.dropbookmarks.resources;

import io.dropwizard.configuration.ConfigurationSourceProvider;
import org.apache.commons.text.StringSubstitutor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Vector;

import static java.util.Objects.requireNonNull;

public class DropMultiConfig implements ConfigurationSourceProvider {
    private final StringSubstitutor substitutor;
    private final ConfigurationSourceProvider delegate;
    /**
     * Returns an {@link InputStream} that contains the source of the configuration for the
     * application. The caller is responsible for closing the result.
     *
     * @parampath the path to the configuration
     * @return an {@link InputStream}
     * @throws IOException if there is an error reading the data at {@code path}
     */
    public DropMultiConfig(ConfigurationSourceProvider delegate, StringSubstitutor substitutor)
    {
      this.substitutor = requireNonNull(substitutor);
      this.delegate = requireNonNull(delegate);
    }
    @Override
    public InputStream open(String path) throws IOException {
        String[] fileNames= path.contains("|") ? path.split("\\|"): new String[]{path};
        Vector<InputStream> inputStream = new Vector<>();
        Arrays.stream(fileNames).forEach(str->
                {
                    try{
                        inputStream.add(delegate.open(str));
                    }
                    catch (IOException e){
                        throw new RuntimeException(e);
                    }
                });

        try(SequenceInputStream seqInputStream = new SequenceInputStream(inputStream.elements()))
        {
            final String config = new String(seqInputStream.readAllBytes(), StandardCharsets.UTF_8);
            final String substituted = substitutor.replace(config);
            System.out.println(substituted+"\n");
            return new ByteArrayInputStream(substituted.getBytes(StandardCharsets.UTF_8));
        }
    }
}
