package ee.ivkhkdev.nptv23libraryjpa.interfaces;

import java.util.Scanner;

public interface Input {
    default String getString(){
        return new Scanner(System.in).nextLine();
    }
    default int getInt(){
        do{
            try{
                return Integer.parseInt(getString());
            }catch (Exception e){
                System.out.println("Пожалуйста, введите число");
            }
        }while (true);
    }
}
