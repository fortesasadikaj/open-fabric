package ai.openfabric.api.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DockerConfig {

    @Value("${docker.host}")
    private String host;

    @Value("${docker.cert-path}")
    private String certPath;

    @Value("${docker.username}")
    private String username;

    @Value("${docker.registry-url}")
    private String registryUrl;

    @Bean
    public DockerClient dockerClientConfig() {
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(host)
                .withDockerTlsVerify(true)
                .withDockerCertPath(certPath)
                .withRegistryUsername(username)
                .withRegistryPassword(null)
                .withRegistryEmail(null)
                .withRegistryUrl(registryUrl)
                .build();


        DockerHttpClient dockerClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();


        DockerClient finalDockerClient = DockerClientImpl.getInstance(config, dockerClient);

        return finalDockerClient;
    }
}
