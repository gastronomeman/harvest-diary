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

		// 设置 Java 虚拟机的 -Djava.awt.headless=true 系统属性
		//System.setProperty("java.awt.headless", "true");


		Login.main(args);
	}

}
