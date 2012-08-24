package org.bk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestSpringCache {

	@Autowired TestService testService;
	
	@Test
	public void testCache() {
		String response1 = testService.cachedMethod("param1", "param2");
		String response2 = testService.cachedMethod("param1", "param2");
		
		assertThat(response2, equalTo(response1));
	}
	
	
	@Configuration
	@EnableCaching(mode=AdviceMode.ASPECTJ)
	@ComponentScan("org.bk")
	public static class TestConfiguration{
		
		@Bean
		public SimpleCacheManager cacheManager(){
			SimpleCacheManager cacheManager = new SimpleCacheManager();
			List<Cache> caches = new ArrayList<Cache>();
			caches.add(cacheBean().getObject());
			cacheManager.setCaches(caches );
			return cacheManager;
		}
		
		@Bean
		public ConcurrentMapCacheFactoryBean cacheBean(){
			ConcurrentMapCacheFactoryBean cacheFactoryBean = new ConcurrentMapCacheFactoryBean();
			cacheFactoryBean.setName("default");
			return cacheFactoryBean;
		}

	}
	
}


