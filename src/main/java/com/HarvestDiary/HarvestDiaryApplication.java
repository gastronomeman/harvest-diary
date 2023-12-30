package com.HarvestDiary;

import com.HarvestDiary.Ui.Login;
import com.HarvestDiary.otherTools.OperationalDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HarvestDiaryApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(HarvestDiaryApplication.class, args);


		Login.main(args);
	}

}
