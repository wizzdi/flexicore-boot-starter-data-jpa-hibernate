package com.wizzdi.flexicore.boot.jpa.hibernate.pluginA;

import com.wizzdi.flexicore.boot.base.interfaces.Plugin;
import com.wizzdi.flexicore.boot.jpa.hibernate.app.TestEntity;
import org.pf4j.Extension;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Component
@Extension
public class PluginARepository implements Plugin {

	@PersistenceContext
	private EntityManager entityManager;



	@Transactional
	public void merge(Object o){
		entityManager.merge(o);
	}


	public TestEntity find(String id) {
		return entityManager.find(TestEntity.class,id);
	}
}
