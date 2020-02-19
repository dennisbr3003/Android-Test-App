package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SecondFragment extends Fragment {


    TextView TodoItemText; //declared on higher level because it is used in a different thread

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_second, container, false);

        // Inflate the layout for this fragment
        View SecondFragmentLayout = inflater.inflate(R.layout.fragment_second, container, false);
        TodoItemText = SecondFragmentLayout.findViewById(R.id.textViewTodoItem);
        return SecondFragmentLayout;
    }

    private void updateTextField(String msg) {
        final String str = msg;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TodoItemText.setText(str);
            }
        });
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        Integer myArg = SecondFragmentArgs.fromBundle(getArguments()).getMyArg();
        TextView textViewHeader = view.findViewById(R.id.textview_header);
        String f = (String) textViewHeader.getText();
        f = String.format(f, myArg); // replace %d with int MyArg (count first fragment)
        textViewHeader.setText(f);

        Integer count = myArg;
        Random random = new java.util.Random();
        Integer randomNumber = 0;
        if (count > 0) {
            randomNumber = random.nextInt(count + 1);
        }


        TextView randomNumberText = view.getRootView().findViewById(R.id.textview_random);
        randomNumberText.setText(randomNumber.toString());

        //textView.setText(getString(R.string.random_heading, myArg));

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(getString(R.string.DebugTest), getString(R.string.ClickOnPrevious));
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.btnUsers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e(getString(R.string.DebugTest), getString(R.string.ClickOnUsers));


                // this is done to lighten the load on the app's main thread. It will cause
                // network failures if you put to much activities in the main thread
                Thread thread = new Thread(new Runnable() {

                    @SuppressLint("LongLogTag")
                    @Override
                    public void run() {
                        try {
                            /*
                            class Response :Closeable
                            An HTTP response. Instances of this class are not immutable:
                            the response body is a one-shot value that may be consumed only once
                            and then closed. All other properties are immutable.
                            */
                            OkHttpClient client = new OkHttpClient().newBuilder()
                                    .build();
                            Request request = new Request.Builder()
                                    .url("https://jsonplaceholder.typicode.com/todos/1") // just to test
                                    .method("GET", null)
                                    .build();

                            Todo_Pojo tp; // wider scope for this object

                            try {

                                ObjectMapper objectMapper = new ObjectMapper();
                                Response response = client.newCall(request).execute();
                                String jsonString = response.body().string(); //only readable once so put in String variable!

                                if(response.isSuccessful()){
                                    try {
                                        tp = objectMapper.readValue(jsonString, Todo_Pojo.class);
                                        updateTextField(tp.getId() + " " + tp.getTitle()); // Has to be done like this because a
                                                                                                 // text field has to be updated in it's original thread
                                    }catch (Exception e) {
                                        Log.e(getString(R.string.MappingException), e.toString());
                                    }
                                }
                                else{
                                    Log.e(getString(R.string.JSONException), getString(R.string.Unexpected));
                                }

                            } catch (IOException e) {
                                Log.e(getString(R.string.IOExcpetion), e.getMessage());
                                return;
                            }
                        } catch (Exception e) {
                            Log.e(getString(R.string.GeneralException), e.getLocalizedMessage());
                            return;
                        }
                    }
                });

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Log.e(getString(R.string.InterruptedException), e.getMessage());
                    return;
                }
            }
        });

        view.findViewById(R.id.button_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getActivity(), R.string.ClickOnTestDetected, Toast.LENGTH_SHORT);
                myToast.show();
                Log.e(getString(R.string.DebugTest), getString(R.string.ClickOnTestDetected));
            }
        });

    }
}
