package com.mkacunha.processadorcep.infrastructure.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_SINGLETON;

@Component
@Scope(value = SCOPE_SINGLETON)
public class ApplicationContextBean {

	private static ApplicationContext applicationContext;

	@Autowired
	public ApplicationContextBean(ApplicationContext applicationContext) {
		ApplicationContextBean.applicationContext = applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

}
