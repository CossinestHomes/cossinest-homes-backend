package com.cossinest.homes.domain.enums;

public enum Cities {

    LONDRA("Londra"),
    MANCHESTER("Manchester"),
    BRISTOL("Bristol"),
    ANKARA("Ankara"),
    ISTANBUL("İstanbul"),
    IZMIR("İzmir"),
    BERLIN("Berlin"),
    HAMBURG("Hamburg"),
    MUNIH("Münih");


        public final String name;

        Cities(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

