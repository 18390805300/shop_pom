package com.lbl.day85_shop_back;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Import(FdfsClientConfig.class)
@ImportResource("classpath:applicationContext-*.xml")
public class Day85ShopBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day85ShopBackApplication.class, args);
	}
}
