package com.kosta.console_rpg.test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProgressBar {
    public static void
    showProgressBar(int length, int interval, String message) {
        char incomplete = '░';
        char complete = '█';
        StringBuilder progress = new StringBuilder();
        // Initialize
        Stream.generate(() -> incomplete).limit(length).forEach(progress::append);
        StringBuilder percent = new StringBuilder("0");
        String format = "\r[%s] %s%%";

        System.out.println(message);
        IntStream.range(0, length).forEach(i -> {
            progress.replace(i, i + 1, String.valueOf(complete));
            percent.delete(0, percent.length()).append(((i + 1) * 100) / length);

            System.out.print(String.format(format, progress, percent));

            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(); // New line after completion
    }

    public static void main(String[] args) {
        showProgressBar(12, 500, "Loading...");
    }
}