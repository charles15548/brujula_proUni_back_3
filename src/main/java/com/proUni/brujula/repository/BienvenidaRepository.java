package com.proUni.brujula.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proUni.brujula.models.Bienvenida;
import com.proUni.brujula.models.DesarrolloPersonal;
@Repository
public interface BienvenidaRepository extends JpaRepository<Bienvenida, Long> {
	
}
