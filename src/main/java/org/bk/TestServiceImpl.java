package org.bk;

import java.util.Random;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class TestServiceImpl implements TestService{
	
//	@Cacheable(value="default", key="#p0.concat('-').concat(#p1)")
	public String cachedMethod(String param1, String param2){
		return cachedMethod2(param1 + param2);
	}
	
	
	@Cacheable(value="default")
	public String cachedMethod2(String param){
		return param + new Random().nextInt();
	}
}