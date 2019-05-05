package com.onagainapps.MIDIRR;
import javax.sound.midi.*;
import java.util.ArrayList;

public class MidiEndpoint implements Receiver, Transmitter {

    private ArrayList<Receiver> receivers;
    private boolean transmitAllMidiToReceiver = true;

    public MidiEndpoint(){

    }

    @Override //receiver override
    public void send(MidiMessage message, long timeStamp) {
        System.out.println(message.getMessage());
        if (transmitAllMidiToReceiver){
            if (receivers != null){
                for (Receiver r:receivers) {
                    r.send(message, timeStamp);
                }
            }
        }
    }

    @Override //transmitter override
    public void setReceiver(Receiver receiver) {
        //just add the receiver
        if (receivers == null){
            receivers = new ArrayList<>();
        }
        receivers.add(receiver);
    }

    @Override //transmitter override
    public Receiver getReceiver() {
        //todo figure out what to do here
        return this.receivers.get(0);
    }

    @Override
    public void close() {

    }
}
