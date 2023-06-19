package com.example.uscream.msg;

import java.io.File;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.example.uscream.account.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MsgDto {
	
	private int msgnum;
	private Account sender;			
	private Account receiver;		
	
	private String title;
	private Date date;
	private String content;
	
	private File file;
	private MultipartFile mfile;
	private String img;
	private MultipartFile mimg;
	
	private int reply;			
	private String mark;		
	private boolean tempcheck;
	private boolean readcheck;		
	private boolean delcheck;		
}	
	