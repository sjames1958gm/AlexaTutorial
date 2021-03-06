package com.netzyn.example;

import android.content.Intent;
import android.util.Log;

import com.netzyn.api.SaApi;
import com.netzyn.api.SaApiInterface;

public class VoiceInterface implements SaApiInterface {
    private final String TAG = "VoiceInterface";

    HelloWorldClientInterface client;
    private SaApi api = null;

    VoiceInterface(HelloWorldClientInterface client, Intent intent)
    {
      // Save client information
      this.client = client;
      // Get the logging tag from client if set.
      if (!client.GetLoggingTag().isEmpty()) {
        TAG = client.GetLoggingTag();
      }
      // Create Streaming apps api object.
      api = new SaApi(intent, this);
    }
    
    // This function is called when an Event Voice message is received.
   // The application must return a voice response to avoid an error by the voice assistant
    public void SaEventVoice(String user, String device, String app, final String sessionId, String intent,
                      final String parm1, final String parm2, final String parm3, final String parm4, final String parm5)
    {
        if (intent.compareToIgnoreCase("hello") == 0) {
            client.HelloWorld(sessionId);
        }
        else if (intent.compareToIgnoreCase("launch") == 0) {
            api.SaVoiceResponse(sessionId, "ask", "Welcome to the hello world application");
        }

        // Other intents shouldn't be delivered to the application, but this catch-all is provided
        api.SaVoiceResponse(sessionId, "tell", "Sorry, I do not understand: " + intent);
    }
    
    public void SendResponse(String sessionId, String msg) {
        api.SaVoiceResponse(sessionId, "ask", msg);
    }

    public void SaEventLog(String var1)
    {
        Log.i(TAG, "Event Log: " + var1);
    }

    public void SaEventConnected()
    {
        Log.i(TAG, "Event Connected");
    }

    public void SaEventError(String var1)
    {
        Log.i(TAG, "Event Error: " + var1);
    }
}
