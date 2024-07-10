package com.xmut.forum;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class XMUTForumApplication {
	public static void main(String[] args) {
		SpringApplication.run(XMUTForumApplication.class, args);
		System.out.println(
			"██╗  ██╗███╗   ███╗██╗   ██╗████████╗    ███████╗ ██████╗ ██████╗ ██╗   ██╗███╗   ███╗\n" +
			"╚██╗██╔╝████╗ ████║██║   ██║╚══██╔══╝    ██╔════╝██╔═══██╗██╔══██╗██║   ██║████╗ ████║\n" +
			" ╚███╔╝ ██╔████╔██║██║   ██║   ██║       █████╗  ██║   ██║██████╔╝██║   ██║██╔████╔██║\n" +
			" ██╔██╗ ██║╚██╔╝██║██║   ██║   ██║       ██╔══╝  ██║   ██║██╔══██╗██║   ██║██║╚██╔╝██║\n" +
			"██╔╝ ██╗██║ ╚═╝ ██║╚██████╔╝   ██║       ██║     ╚██████╔╝██║  ██║╚██████╔╝██║ ╚═╝ ██║\n" +
			"╚═╝  ╚═╝╚═╝     ╚═╝ ╚═════╝    ╚═╝       ╚═╝      ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝\n"
		);
	}
}
