package com.femcoders;

import com.femcoders.config.DBManager;

public class Main {
    public static void main(String[] args) {

        DBManager.getConnection();
        DBManager.closeConnection();

        }
    }