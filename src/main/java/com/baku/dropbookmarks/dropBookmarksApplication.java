package com.baku.dropbookmarks;

import com.baku.dropbookmarks.resources.DropMultiConfig;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import com.baku.dropbookmarks.auth.TestAuthenticator;
import com.baku.dropbookmarks.core.User;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.eclipse.jetty.security.DefaultAuthenticatorFactory;

import com.baku.dropbookmarks.resources.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

public class dropBookmarksApplication extends Application<dropBookmarksConfiguration> {

    private static final Logger log = LoggerFactory.getLogger(dropBookmarksApplication.class.getSimpleName());

    public static void main(final String[] args) throws Exception {
        new dropBookmarksApplication().run(args);
       
    }

    @Override
    public String getName() {
        return "dropBookmarks";
    }

    @Override
    public void initialize(final Bootstrap<dropBookmarksConfiguration> bootstrap) {
        // TODO: application initialization
        //Enable variable substitution with environment variable
        EnvironmentVariableSubstitutor substitutor = new EnvironmentVariableSubstitutor(false);
        DropMultiConfig provider = new DropMultiConfig(bootstrap.getConfigurationSourceProvider(), substitutor);
        bootstrap.setConfigurationSourceProvider(provider);
    }

    @Override
    public void run(final dropBookmarksConfiguration configuration,
                    final Environment environment) throws InterruptedException {
    	
    	environment.jersey().register(new HelloController()
    			);
    	
    	environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
    	        .setAuthenticator(new TestAuthenticator())
    	        .setRealm("SECURITY REALM")
    	        .buildAuthFilter()));
    	environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        System.out.println("COUNTRY NAME IS "+configuration.getCountryName());
        String flag="";
        if("K".equalsIgnoreCase(flag))
            kafkaProducer();
        else if("M".equalsIgnoreCase(flag))
            mqttClient();

    }
  private static void kafkaProducer()
  {
      log.info("Hey there.....");

      // Create producer properties
      Properties properties = new Properties();
      //properties.setProperty("bootstrap.server","127.0.0.1:9092"); // for local connectivity
      properties.setProperty("bootstrap.servers","https://key-octopus-5942-eu2-kafka.upstash.io:9092");
      properties.setProperty("sasl.mechanism","SCRAM-SHA-256");
      properties.setProperty("security.protocol","SASL_SSL");
      properties.setProperty("sasl.jaas.config"
              ,"org.apache.kafka.common.security.scram.ScramLoginModule required username=\"a2V5LW9jdG9wdXMtNTk0MiTiWdAjHx9a5jHRaxadgDIcibuq-CAu-J_qM2OzRFo\" password=\"NzA5ZGY2YzYtNTlkZS00ZGI3LThkN2MtYWIyM2RlOGU1OWEx\";");
      properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
      properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer");

      //properties.setProperty("batch.size","400");

      //Not Recommended in production due to performance issue
      //properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName());

      //Create the producer
      KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

      //Create producer record withot key
      ProducerRecord<String, String> producerRecord =
              new ProducerRecord<>("baku_first_topic","Hello Baku!!!__2");

      //Create producer record with key
      String topic="baku_first_topic";
      String key="id_1";
      String value="This is value with key...";
      ProducerRecord<String, String> producerRecordWithKey =
              new ProducerRecord<>(topic,key,value);


      //Send data
     // producer.send(producerRecord); // Asynchronous operation

      //Producer with callback operation
      producer.send(producerRecordWithKey,(recordMetadata, e) -> {
          if(e==null)
          {
              log.info("\nReceived new metadata..\n"+
                      "Topic: "+recordMetadata.topic()+"\n" +
                      "Partition: "+recordMetadata.partition()+"\n" +
                      "Offset: "+recordMetadata.offset()+"\n" +
                      "Timestamp: "+recordMetadata.timestamp()+"\n" +
                      "hasOffset: "+recordMetadata.hasOffset()+"\n" );
          }
          else
          {
              log.error("There is an error: "+e);
          }
      });

      //tell the producer send all data and block until done -- Synchronous operation
      producer.flush();

      // Flush and close the producer
      producer.close();
      log.info("Data sent to kafka topic....");
  }
  private static void mqttClient()
  {
      log.info("This is MQTT client....");

      final String host = "6aae6a3e4d20489eab946259ec102e0f.s1.eu.hivemq.cloud";
      final String username = "debarshi";
      final String password = "Baku1234.";

      // create an MQTT client
      final Mqtt5BlockingClient client = MqttClient.builder()
              .useMqttVersion5()
              .serverHost(host)
              .serverPort(8883)
              .sslWithDefaultConfig()
              .buildBlocking();

      // connect to HiveMQ Cloud with TLS and username/pw
      client.connectWith()
              .simpleAuth()
              .username(username)
              .password(UTF_8.encode(password))
              .applySimpleAuth()
              .send();

      log.info("Connected successfully");

      // subscribe to the topic "my/test/topic"
      client.subscribeWith()
              .topicFilter("test/baku/mytopic")
              .send();

      // set a callback that is called when a message is received (using the async API style)
      client.toAsync().publishes(ALL, publish -> {
          log.info("Received message: " +
                  publish.getTopic() + " -> " +
                  UTF_8.decode(publish.getPayload().get()));

          // disconnect the client after a message was received
          //client.disconnect();
      });

      // publish a message to the topic "my/test/topic"
      /*client.publishWith()
              .topic("my/test/topic")
              .payload(UTF_8.encode("Hello"))
              .send();*/
  }

}
