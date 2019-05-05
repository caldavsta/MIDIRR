package com.onagainapps.MIDIRR.controllers;

import com.onagainapps.MIDIRR.InOutDirector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.Observable;

public class MainWindowController {
    @FXML
    ListView hw_endpoint_input;
    @FXML
    ListView<String> hw_endpoint_output;
    @FXML
    ListView<String> sw_endpoint_input;
    @FXML
    ListView<String> sw_endpoint_output;

    @FXML
    Button button_sw_start;
    @FXML
    Button button_sw_stop;
    @FXML
    Button button_hw_start;
    @FXML
    Button button_hw_stop;

    public void initialize(){
        //get a list of devices
        setupUi();
    }

    private void setupUi(){
        try {
            refreshDeviceListViews();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void setListViewsDisable(Boolean disabled){
        hw_endpoint_input.setDisable(disabled);
        hw_endpoint_output.setDisable(disabled);
        sw_endpoint_input.setDisable(disabled);
        sw_endpoint_output.setDisable(disabled);
    }

    private void refreshDeviceListViews() throws MidiUnavailableException {
        setListViewsDisable(false);

        ObservableList<String> devicesWithRecievers = GetObservableInfoListFromArray(
                InOutDirector.GetDevicesThatHaveReceivers()
        );
        ObservableList<String> devicesWithTransmitters = GetObservableInfoListFromArray(
                InOutDirector.GetDevicesThatHaveTransmitters()
        );
        hw_endpoint_input.setItems(devicesWithRecievers);
        sw_endpoint_input.setItems(devicesWithRecievers);
        hw_endpoint_output.setItems(devicesWithTransmitters);
        sw_endpoint_output.setItems(devicesWithTransmitters);
    }

    public static ObservableList<String> GetObservableInfoListFromArray(MidiDevice.Info[] infos){
        ObservableList<String> result = FXCollections.observableArrayList();

        String stringToAdd = "";
        for (MidiDevice.Info info : infos){
            stringToAdd = info.getName()
                    + " " + info.getVendor()
                    + " " + info.getVersion()
                    + " " + info.getDescription();
            result.add(stringToAdd);
            System.out.println(stringToAdd);
        }

        return result;
    }





}
