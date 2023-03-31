package com.jpmc;

import com.jpmc.command.factory.CommandFactory;
import com.jpmc.exception.BusinessException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while(true) {
            System.out.println("Select User (1)Admin (2)Buyer : ");
            int user = new Scanner(System.in).nextInt();

            if(user != CommandFactory.ADMIN && user != CommandFactory.BUYER) {
                System.out.println("Invalid user");
                continue;
            }

            try {
                System.out.println("Enter Command : ");
                String command = new Scanner(System.in).nextLine();
                String output = CommandFactory.createCommand(user, command).execute();
                System.out.println(output);
            }
            catch (BusinessException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }
}
