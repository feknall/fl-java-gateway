package com.example.fljavagateway;

public class SocketMessageDto {
    private String name;
    private byte[] payload;


    public SocketMessageDto(String name, byte[] payload) {
        this.payload = payload;
        this.name = name;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
}
