package com.example.dummybank.demo;

import com.rabbitmq.client.ConnectionFactory;
import model.MonthlyTransfer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api")
public class Controller {
	String api="[880DA5360C3DE5960B9168CEFDE5C803]";
	@GetMapping("/checkTypes/{bloodType}")
	public ResponseEntity checkIfBloodTypeExists(@RequestHeader HttpHeaders http,@PathVariable String bloodType) {
		if(api.equals(http.get("x-api-key").toString())) {
//			int x = 1/0;
			Random rand = new Random();
			boolean retVal;
			if (rand.nextFloat() > 0.5)
				retVal = true;
			else
				retVal = false;
			return ResponseEntity.ok(retVal);
		}else{
			System.out.println("ULAZIM ODJE");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/{bloodType}/{amount}")
	public ResponseEntity<Boolean> checkBloodTypeAmmount(@PathVariable String bloodType,@PathVariable double amount) {
		return ResponseEntity.ok(false);
	}

	@PostMapping("/sendPdf")
		public ResponseEntity sendPdf(@RequestBody MultipartFile pdf) throws Exception{
			System.out.println("USAO SAM ODJE");
		System.out.println(pdf.getOriginalFilename());
		String filePath = "..\\PDF\\"+pdf.getOriginalFilename();
		Files.copy(pdf.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
			return ResponseEntity.ok("As");
		}
	@PostMapping(value="/monthlyTransfer",consumes = MediaType.APPLICATION_JSON_VALUE)
	public String monthlyTransfer(@RequestBody MonthlyTransfer mt) throws IOException, TimeoutException {
		System.out.println("Monhtly Transfer Service worked.");

		var factory = new ConnectionFactory();
		factory.setHost("localhost");

		var connection = factory.newConnection();
		var channel = connection.createChannel();

		channel.queueDeclare("monthly", false, false, false, null);

		var message = "A_PLUS:"+mt.APlus+"-"+"A_MINUS:"+mt.AMinus+"-"+"B_PLUS:"+mt.BPlus+"-"+"B_MINUS:"+mt.BMinus+"-"+"AB_PLUS:"+mt.ABPlus+"-"+"AB_MINUS:"+mt.ABMinus+"-"+"O_PLUS:"+mt.OPlus+"-"+"O_MINUS:"+mt.OMinus;

		byte[] body = message.getBytes(StandardCharsets.UTF_8);

		channel.basicPublish("", "monthly", null, body);

		return "BLA";
	}
}
