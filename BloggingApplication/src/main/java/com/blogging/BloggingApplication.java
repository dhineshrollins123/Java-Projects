package com.blogging;

import com.blogging.config.AppConstants;
import com.blogging.entities.Role;
import com.blogging.repositories.RoleRepository;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	private String dbUrl;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	private DataSource dataSource;

	public DataSource getDataSource() {

		if(dbUrl == null){
			return new HikariDataSource();
		}else {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(dbUrl);
			return new HikariDataSource(config);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		Role role = new Role();
		role.setRoleId(AppConstants.ADMIN_ACCESS);
		role.setRoleName("ADMIN");

		Role role2 = new Role();
		role2.setRoleId(AppConstants.USER_ACCESS);
		role2.setRoleName("USER");

		List<Role> roles = List.of(role, role2);
		roleRepository.saveAll(roles);
	}
}
