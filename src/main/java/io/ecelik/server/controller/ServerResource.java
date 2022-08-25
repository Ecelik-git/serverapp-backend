package io.ecelik.server.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static java.time.LocalDateTime.now;
import io.ecelik.server.model.Response;
import io.ecelik.server.model.Server;
import io.ecelik.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.CREATED;
import static io.ecelik.server.enumeration.Status.SERVER_UP;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {
	
	private final ServerService serverService;
	
	@GetMapping("/list")
	public ResponseEntity<Response> getServers() throws InterruptedException{
		TimeUnit.SECONDS.sleep(3);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("servers", serverService.list(30)))
				.message("servers retrieved")
				.status(OK)
				.statusCode(OK.value())
				.build());
				
				
				
	}
	
	@GetMapping("/ping/{ipAddress}")
	public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") 
	String ipAddress) throws IOException{
		
		Server server = serverService.ping(ipAddress);
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("servers", server))
				.message(server.getStatus() == SERVER_UP ? "Ping Success": "Ping Failed")
				.status(OK)
				.statusCode(OK.value())
				.build());
				
	}
	
	@PostMapping("/save")
	public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server){
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("server", serverService.create(server)))
				.message("server is created")
				.status(CREATED)
				.statusCode(CREATED.value())
				.build());
				
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getServer(@PathVariable("id") Long id){
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("servers", serverService.get(id)))
				.message("Server is retrieved")
				.status(OK)
				.statusCode(OK.value())
				.build());
				
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id){
		return ResponseEntity.ok(
				Response.builder()
				.timeStamp(now())
				.data(Map.of("deleted", serverService.delete(id)))
				.message("Server is deleted")
				.status(OK)
				.statusCode(OK.value())
				.build());
				
	}
	
	@GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
	public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException{
		return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"Downloads/images/" +fileName));
				
	}
	
 
}
