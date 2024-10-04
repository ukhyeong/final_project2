	package org.zerock.myapp.common;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@NoArgsConstructor
public class CommonEntityLifecyleListener {

	
	@PostLoad
	void postLoad(Object entity) {
		log.trace("1. postLoad({}) invoked.", entity);
	} // postLoad
	
	@PrePersist
	void prePersist(Object entity) {
		log.trace("2. prePersist({}) invoked.", entity);
	} // PrePersist
	
	@PostPersist
	void postPersist(Object entity) {
		log.trace("3. postPersist({}) invoked.", entity);
	} // postLoad
	
	@PreUpdate
	void preUpdate(Object entity) {
		log.trace("4. preUpdate({}) invoked.", entity);
	} // PreUpdate
	
	@PostUpdate
	void postUpdate(Object entity) {
		log.trace("5. postUpdate({}) invoked.", entity);
	} // PostUpdate
	
	@PreRemove
	void preRemove(Object entity) {
		log.trace("6. preRemove({}) invoked.", entity);
	} // PreRemove
	
	@PostRemove
	void postRemove(Object entity) {
		log.trace("7. postRemove({}) invoked.", entity);
	} // postRemove

} // end class
