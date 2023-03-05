package Task1Asynchronous;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {

		Ring res = new Ring();
		int size = res.initialize();
		Scheduler solution = new Scheduler(res);
		if (size<=10) {
			UI visualize = new UI(res,size,solution.getSystemCounter());
			visualize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			visualize.setSize(1000, 1000);
			visualize.show();
			solution.LCR(visualize);
		}else {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println("Ring is too large, can not visualize well");
			solution.LCR();
		}
		
	}

}
