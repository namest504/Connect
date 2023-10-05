package xyz.connect.user.config;

import java.sql.SQLException;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("develop")
@Configuration
public class H2Config {

    @Bean
    Server h2Tcp() throws SQLException {
        return Server.createTcpServer().start();
    }
}
