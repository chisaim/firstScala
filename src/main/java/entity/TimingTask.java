package entity;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimingTask {

    private ScheduledExecutorService scheduExec;

    public TimingTask() {
        this.scheduExec = Executors.newScheduledThreadPool(2);
    }

    public void timerTwo() {
        scheduExec.scheduleAtFixedRate(new Runnable() {
            public void run() {
                Path dir = Paths.get("D:\\Download\\SogouQ3.txt");

                if (dir.toFile().exists()) {
//                    System.out.println("start scanning path is " + dir.toUri());

                    try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)){
                        for(Path e : stream){
                            System.out.println(e.getFileName());
                        }
                    }catch(IOException e){

                    }

                } else {
                    dir.toFile().mkdirs();
                    System.out.println("This directory has been created :" + dir.toFile().getPath());
                }


            }
        }, 2000, 3000, TimeUnit.MILLISECONDS);

    }

    public static void main(String[] args) {
        TimingTask test = new TimingTask();
        test.timerTwo();
    }

}