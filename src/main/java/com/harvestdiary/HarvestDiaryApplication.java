package com.harvestdiary;

import com.harvestdiary.ui.Login;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HarvestDiaryApplication {
	public static void main(String[] args) throws IOException {
		/*if (!OperationalDocument.existFile("userStatus.json")){
			UserStatus status = new UserStatus();
			OperationalDocument.saveFile("userStatus.json", JSONUtil.toJsonStr(status));
		}*/
		SpringApplication.run(HarvestDiaryApplication.class, args);
		Login.main(args);
	}

}
