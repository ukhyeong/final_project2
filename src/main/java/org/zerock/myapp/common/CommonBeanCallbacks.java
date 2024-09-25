package org.zerock.myapp.common;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//@Log4j2
@Slf4j

@NoArgsConstructor
public abstract class CommonBeanCallbacks
	implements InitializingBean, DisposableBean, BeanNameAware {
	
	protected String beanName;

	
	@Override
	public void setBeanName(String name) {
		log.trace("setBeanName({}) invoked.", name);
		this.beanName = name;
	} // setBeanName
	
	@PostConstruct
	public void postConstruct() {} // postConstruct
	
	@PreDestroy
	public void preDestroy() {} // preDestroy
	
	@Override
	public void destroy() throws Exception {} // destroy

	@Override
	public void afterPropertiesSet() throws Exception {} // afterPropertiesSet

} // end class
