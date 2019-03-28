package ro.pub.cs.systems.eim.practicaltest01var02;

import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private Random random = new Random();

    private double sum;
    private double dif;

    public ProcessingThread(Context context, int firstNumber, int secondNumber) {
        this.context = context;

        sum = (firstNumber + secondNumber);
        dif = (firstNumber - secondNumber);
    }

    @Override
    public void run() {
        Log.d("[ProcessingThread]", "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d("[ProcessingThread]", "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[random.nextInt(Constants.actionTypes.length)]);
//        intent.setAction(String.valueOf(19));
        intent.putExtra("message", new Date(System.currentTimeMillis()) + " " + sum + " " + dif);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
