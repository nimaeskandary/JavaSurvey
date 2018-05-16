package com.nimaeskandary.model;

import java.io.Serializable;

abstract public class Answer implements Serializable {
    abstract public String toString();
    abstract Answer makeCopy();
    abstract boolean equals(Answer other);
}