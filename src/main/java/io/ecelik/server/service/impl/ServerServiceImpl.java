package io.ecelik.server.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static io.ecelik.server.enumeration.Status.SERVER_UP;
import static io.ecelik.server.enumeration.Status.SERVER_DOWN;
import io.ecelik.server.model.Server;
import io.ecelik.server.repo.ServerRepo;
import io.ecelik.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService {
	
	private final ServerRepo serverRepo;
	
	

	@Override
	public Server create(Server server) {
		log.info("saving new server: {}", server.getName());
		server.setImageUrl(setServerImageUrl());
		return serverRepo.save(server);
	}
	
	private String setServerImageUrl() {
		String[] imageNames = {"server1.png", "server2.png", "server3.png", "server4.png"};
		
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/server/image/" + imageNames[new Random().nextInt(4)]).toUriString();
	}

	@Override
	public Collection<Server> list(int limit) {
		log.info("Fetching all servers");
		return serverRepo.findAll(PageRequest.of(0, limit)).toList();
		
	}

	@Override
	public Server get(Long id) {
		log.info("Fetching Server by id: {}", id);
		return serverRepo.findById(id).get();
	}

	@Override
	public Server update(Server server) {
		log.info("Updating server: {}", server);
		return serverRepo.save(server);
	}

	@Override
	public Boolean delete(Long id) {
		log.info("Deleting server by Id: {}", id);
		serverRepo.deleteById(id);
		return Boolean.TRUE;
	}

	@Override
	public Server ping(String ipAddress){
		log.info("Pinging server IP: {}", ipAddress);
		Server server = serverRepo.findByIpAddress(ipAddress);
		InetAddress address = null;
		try {
			address = InetAddress.getByName(ipAddress);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			server.setStatus(address.isReachable(10000) ? SERVER_UP : SERVER_DOWN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		serverRepo.save(server);
		
		return server;
	}

}
