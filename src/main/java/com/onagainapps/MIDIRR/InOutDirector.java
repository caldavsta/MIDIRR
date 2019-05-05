package com.onagainapps.MIDIRR;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;

public class InOutDirector {

    public static void CloseAllResources(){
        for (AutoCloseable autoCloseable : resourcesToClose){
            try {
                autoCloseable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<AutoCloseable> resourcesToClose;
    public static ArrayList<AutoCloseable> GetResourcesToClose()
    {
        if (resourcesToClose == null)
            resourcesToClose = new ArrayList<AutoCloseable>();

        return resourcesToClose;
    }

    private static MidiEndpoint _hardwareEndpoint;
    private static MidiEndpoint _softwareEndpoint;
    public static MidiEndpoint GetHardwareEndpoint()
    {
        if (_hardwareEndpoint == null)
            _hardwareEndpoint = new MidiEndpoint();

        return _hardwareEndpoint;
    }

    public static MidiEndpoint GetSoftwareEndpoint()
    {
        if (_softwareEndpoint == null)
            _softwareEndpoint = new MidiEndpoint();

        return _softwareEndpoint;
    }


    public static MidiDevice GetDeviceByInfo(MidiDevice.Info info) throws MidiUnavailableException {
        MidiDevice result;
        result = MidiSystem.getMidiDevice(info);
        return result;
    }

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
