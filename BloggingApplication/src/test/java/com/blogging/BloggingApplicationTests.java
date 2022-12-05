package com.blogging;

import com.blogging.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BloggingApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void userRepoTest() {
		String className = userRepository.getClass().getName();
		String packageName = userRepository.getClass().getPackageName();

		System.out.println("class name : "+className);
		System.out.println("package name : "+packageName);
	}

}
