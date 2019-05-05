package com.onagainapps.MIDIRR;
import javax.sound.midi.*;

public class MidiEndpoint implements Receiver, Transmitter {

    private Receiver receiver;
    private boolean transmitAllMidiToReceiver = true;

    public MidiEndpoint(){

    }

    @Override //receiver override
    public void send(MidiMessage message, long timeStamp) {
        System.out.println(message.getMessage());
        if (transmitAllMidiToReceiver){
            if (receiver != null){
                receiver.send(message, timeStamp);
            }
        }
    }

    @Override //transmitter override
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override //transmitter override
    public Receiver getReceiver() {
        return this.receiver;
    }

    @Override
    public void close() {

    }
}
