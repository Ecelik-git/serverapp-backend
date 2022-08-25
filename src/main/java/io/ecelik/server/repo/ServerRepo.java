package io.ecelik.server.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ecelik.server.model.Server;

public interface ServerRepo extends JpaRepository<Server, Long> {

	Server findByIpAddress(String ipAddress);
	

}
