package com.terriblehacks.simplekayboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

public class SimpleIME extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;

    String johnCena;
    MediaPlayer mp;
    int maxVolume = 50;
    int curVol = 10;

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);

        mp = MediaPlayer.create(this, R.raw.john_cena);
        //mp.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //mp.setStreamVolume(AudioManager.STREAM_MUSIC, curVol, 0);


        return kv;
    }

    private void playClick(int keyCode){
        AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        mp.start();

        switch(keyCode){
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                mp.pause();
                //mp.reset();
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
                am.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        }
    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch(primaryCode){
            case Keyboard.KEYCODE_DELETE :
                ic.deleteSurroundingText(1, 0);

                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            default:
                char code = (char)primaryCode;
                if(Character.isLetter(code) && caps){
                    code = Character.toUpperCase(code);
                }

                switch(code){
                    case 'z':
                        johnCena = "j jjoooohn";
                        break;
                    case 'x':
                        johnCena = "CCCCCCEEENAAAAAA";
                        break;
                    case 'y':
                        johnCena = "DO";
                        break;
                    case 'w':
                        johnCena = "dodo";
                        break;
                    case 'v':
                        johnCena = "DOO)))))OOO";
                        break;
                    case 't':
                        johnCena = "JJJJOOOOOOHHHNNNN";
                        break;
                    default:
                        ic.commitText(String.valueOf(code),1);
                        return;
                }
                ic.commitText(johnCena,1);

        }
    }


    @Override
    public void onPress(int primaryCode) {
    }

    @Override
    public void onRelease(int primaryCode) {
    }

    @Override
    public void onText(CharSequence text) {
    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeLeft() {
    }

    @Override
    public void swipeRight() {
    }

    @Override
    public void swipeUp() {
    }
}

