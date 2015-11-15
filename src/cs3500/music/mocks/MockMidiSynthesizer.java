package cs3500.music.mocks;


import java.util.List;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.midi.VoiceStatus;

public class MockMidiSynthesizer implements Synthesizer {
    MockMidiChannel[] channels;
    MockLogger logger;

    public MockMidiSynthesizer() {
        this.channels = new MockMidiChannel[10];
        for (int i = 0; i < this.channels.length; i++) {
            this.channels[i] = new MockMidiChannel();
        }
        this.logger = MockLogger.getInstance();
        this.logger.log("Created MockMidiSynthesizer");
    }

    @Override
    public int getMaxPolyphony() {
        return 0;
    }

    @Override
    public long getLatency() {
        return 0;
    }

    public MockMidiChannel[] getChannels() {
        return this.channels;
    }

    @Override
    public VoiceStatus[] getVoiceStatus() {
        return new VoiceStatus[0];
    }

    @Override
    public boolean isSoundbankSupported(Soundbank soundbank) {
        return false;
    }

    @Override
    public boolean loadInstrument(Instrument instrument) {
        return false;
    }

    @Override
    public void unloadInstrument(Instrument instrument) {

    }

    @Override
    public boolean remapInstrument(Instrument from, Instrument to) {
        return false;
    }

    @Override
    public Soundbank getDefaultSoundbank() {
        return null;
    }

    @Override
    public Instrument[] getAvailableInstruments() {
        return new Instrument[0];
    }

    @Override
    public Instrument[] getLoadedInstruments() {
        return new Instrument[0];
    }

    @Override
    public boolean loadAllInstruments(Soundbank soundbank) {
        return false;
    }

    @Override
    public void unloadAllInstruments(Soundbank soundbank) {

    }

    @Override
    public boolean loadInstruments(Soundbank soundbank, Patch[] patchList) {
        return false;
    }

    @Override
    public void unloadInstruments(Soundbank soundbank, Patch[] patchList) {

    }

    @Override
    public Info getDeviceInfo() {
        return null;
    }

    @Override
    public void open() throws MidiUnavailableException {
        this.logger.log("Opened MockMidiSynthesizer");
    }

    @Override
    public void close() {
        this.logger.log("Closed MockMidiSynthesizer");
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public long getMicrosecondPosition() {
        return 0;
    }

    @Override
    public int getMaxReceivers() {
        return 0;
    }

    @Override
    public int getMaxTransmitters() {
        return 0;
    }

    @Override
    public Receiver getReceiver() throws MidiUnavailableException {
        return null;
    }

    @Override
    public List<Receiver> getReceivers() {
        return null;
    }

    @Override
    public Transmitter getTransmitter() throws MidiUnavailableException {
        return null;
    }

    @Override
    public List<Transmitter> getTransmitters() {
        return null;
    }
}

