package com.mwos.ebochs.core.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AsmAdapt {
	private String file1;
	private String file2;

	private String[] content;

	public AsmAdapt(String file1, String file2) {
		this.file1 = file1;
		this.file2 = file2;
	}

	public void init() throws IOException {
		List<String> temp = new ArrayList<>();
		BufferedReader br1 = new BufferedReader(new FileReader(file1));
		BufferedReader br2 = new BufferedReader(new FileReader(file2));
		String line1 = "";

		while ((line1 = br1.readLine()) != null) {
			if(line1.startsWith("_")) {
				
			}
		}

	}
	
	private String nextFunLine(BufferedReader br2) {
		
	}
	
	private String nextLine(BufferedReader br2,String ) {
		
	}
}
