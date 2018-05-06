package com.nimaeskandary;

abstract public class Answer{
    abstract public String toString();
    abstract Answer makeCopy();
    abstract boolean equals(Answer other);
}