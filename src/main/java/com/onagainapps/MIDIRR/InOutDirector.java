package com.onagainapps.MIDIRR;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Transmitter;
import java.util.ArrayList;
import java.util.List;

public class InOutDirector {

    public static MidiDevice.Info[] GetDevicesThatHaveTransmitters() throws MidiUnavailableException {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        ArrayList<MidiDevice.Info> resultAsList = new ArrayList<MidiDevice.Info>();

        for (MidiDevice.Info info : infos) {
            MidiDevice device;
            device = MidiSystem.getMidiDevice(info);
            System.out.println("Found: " + device);

            int maxTransmitters = device.getMaxTransmitters();
            System.out.println("  Max transmitters: " + maxTransmitters);

            if (maxTransmitters == -1 || maxTransmitters > 0) {
                resultAsList.add(info);
            }
        }

        return MidiDeviceInfoListToArray(resultAsList);
    }

    public static MidiDevice.Info[] GetDevicesThatHaveReceivers() throws MidiUnavailableException {
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        ArrayList<MidiDevice.Info> resultAsList = new ArrayList<MidiDevice.Info>();

        for (MidiDevice.Info info : infos) {
            MidiDevice device;
            device = MidiSystem.getMidiDevice(info);
            System.out.println("Found: " + device);

            int maxReceivers = device.getMaxReceivers();
            System.out.println("  Max recievers: " + maxReceivers);

            if (maxReceivers == -1 || maxReceivers > 0) {
                resultAsList.add(info);
            }
        }

        return MidiDeviceInfoListToArray(resultAsList);
    }

    private static MidiDevice.Info[] MidiDeviceInfoListToArray(ArrayList<MidiDevice.Info> input){
        MidiDevice.Info[] result = new MidiDevice.Info[input.size()];
        for (int i = 0; i < input.size() ; i ++){
            result[i] = input.get(i);
        }
        return result;
    }
}
