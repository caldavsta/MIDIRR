package com.onagainapps.MIDIRR.controllers;

import com.onagainapps.MIDIRR.InOutDirector;
import com.onagainapps.MIDIRR.MidiEndpoint;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.Observable;

public class MainWindowController {
    @FXML
    ListView hw_endpoint_input;
    @FXML
    ListView hw_endpoint_output;
    @FXML
    ListView sw_endpoint_input;
    @FXML
    ListView sw_endpoint_output;

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
        setupListeners();
        setCellFactory(hw_endpoint_input);
        setCellFactory(hw_endpoint_output);
        setCellFactory(sw_endpoint_input);
        setCellFactory(sw_endpoint_output);

        try {
            refreshDeviceListViews();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void setupListeners(){
        hw_endpoint_output.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MidiDevice.Info>() {
            @Override
            public void changed(ObservableValue<? extends MidiDevice.Info> observable, MidiDevice.Info oldValue, MidiDevice.Info newValue) {
                ///only change the device if none has been selected
                if (oldValue == null){
                    try {
                        MidiDevice device = MidiSystem.getMidiDevice(newValue);
                        Transmitter transmitter = device.getTransmitter();
                        transmitter.setReceiver(InOutDirector.GetHardwareEndpoint());
                        InOutDirector.GetResourcesToClose().add(device);
                        device.open();
                    } catch (MidiUnavailableException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        hw_endpoint_input.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MidiDevice.Info>() {
            @Override
            public void changed(ObservableValue<? extends MidiDevice.Info> observable, MidiDevice.Info oldValue, MidiDevice.Info newValue) {
                ///only change the device if none has been selected
                if (oldValue == null){
                    try {
                        MidiDevice device = MidiSystem.getMidiDevice(newValue);
                        Receiver receiver = device.getReceiver();
                        InOutDirector.GetHardwareEndpoint().setReceiver(receiver);
                        InOutDirector.GetResourcesToClose().add(device);
                        device.open();
                    } catch (MidiUnavailableException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    private void setCellFactory(ListView listView){
        listView.setCellFactory(param -> new ListCell<MidiDevice.Info>() {
            @Override
            protected void updateItem(MidiDevice.Info item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }
    private void setListViewsDisable(Boolean disabled){
        hw_endpoint_input.setDisable(disabled);
        hw_endpoint_output.setDisable(disabled);
        sw_endpoint_input.setDisable(disabled);
        sw_endpoint_output.setDisable(disabled);
    }

    private void refreshDeviceListViews() throws MidiUnavailableException {
        setListViewsDisable(false);

        ObservableList<MidiDevice.Info> devicesWithRecievers = GetObservableInfoListFromArray(
                InOutDirector.GetDevicesThatHaveReceivers()
        );
        ObservableList<MidiDevice.Info> devicesWithTransmitters = GetObservableInfoListFromArray(
                InOutDirector.GetDevicesThatHaveTransmitters()
        );
        hw_endpoint_input.setItems(devicesWithRecievers);
        sw_endpoint_input.setItems(devicesWithRecievers);
        hw_endpoint_output.setItems(devicesWithTransmitters);
        sw_endpoint_output.setItems(devicesWithTransmitters);
    }

    public static ObservableList<MidiDevice.Info> GetObservableInfoListFromArray(MidiDevice.Info[] infos){
        ObservableList<MidiDevice.Info> result = FXCollections.observableArrayList();

        //String stringToAdd = "";
        for (MidiDevice.Info info : infos){
        //    stringToAdd = info.getName()
        //            + " " + info.getVendor()
        //            + " " + info.getVersion()
        //            + " " + info.getDescription();
            result.add(info);
        }

        return result;
    }





}
