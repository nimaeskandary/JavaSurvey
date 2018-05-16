package com.nimaeskandary;

import com.nimaeskandary.controller.AppController;
import com.nimaeskandary.repository.FileSystemRepository;

public class Main {
    public static void main(String[] args) {
        AppController client = new AppController(System.in, System.out, new FileSystemRepository("resources"));
        client.menuScreen();
    }
}
