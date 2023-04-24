package com.example.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing
@SpringBootApplication
public class CachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		CachingProvider cachingProvider = Caching.getCachingProvider();
//		CacheManager cacheManager = cachingProvider.getCacheManager();
//		MutableConfiguration<Long, String> config = new MutableConfiguration<Long, String>()
//			.setTypes(Long.class, String.class)
//			.setStoreByValue(false)
//			.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
//
//		Cache<Long, String> cache = cacheManager.createCache("jCache", config);
//		System.out.println("cachingProvider = " + cachingProvider);
//		cache.put(1L, "data-one");
//		String value = cache.get(1L);
//
//		System.out.println("caching에 저장된 내용 출력: " + value);
//
//	}

}
