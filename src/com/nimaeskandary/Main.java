package com.nimaeskandary;

public class Main {
    public static void main(String[] args) {
        Client client = new Client(System.in, System.out, new FileSystemRepository(""));
        client.start();
    }
}
