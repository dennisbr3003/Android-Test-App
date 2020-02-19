package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.example.myapplication.R.drawable.eye_off_ctulhu;
import static com.example.myapplication.R.drawable.golem;
import static com.example.myapplication.R.drawable.ic_launcher_foreground;
import static com.example.myapplication.R.drawable.moonlord_full;
import static com.example.myapplication.R.drawable.skel_pime;
import static com.example.myapplication.R.drawable.ter_boss;

public class FirstFragment extends Fragment {

    TextView showCountTextView;

    public class PopUpClass {

        //PopupWindow display method

        public void showPopupWindow(final View view) {


            //Create a View object yourself through inflater
            LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.pop_up_layout, null);

            //Specify the length and width through constants
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.MATCH_PARENT;

            //Make Inactive Items Outside Of PopupWindow
            boolean focusable = true;

            //Create a window with our parameters
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            //Set the location of the window on the screen
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            //Initialize the elements of our window, install the handler

            TextView test2 = popupView.findViewById(R.id.titleText);
            test2.setText(R.string.textTitle);

            Button buttonEdit = popupView.findViewById(R.id.messageButton);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //As an example, display the message
                    Toast.makeText(view.getContext(), "Deze knop doet alleen dit Dani\u00ebl :(", Toast.LENGTH_SHORT).show();

                }
            });


            //Handler for clicking on the inactive zone of the window

            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    //Close the window when clicked
                    popupWindow.dismiss();
                    Switch sw1 = view.findViewById(R.id.switchPopup); // in case you do not use the switch
                    if(sw1 != null) {
                        sw1.setChecked(false);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View firstFragmentLayout = inflater.inflate(R.layout.fragment_first, container, false);
        showCountTextView = firstFragmentLayout.findViewById(R.id.text_random);
        return firstFragmentLayout;
    }

    public void onViewCreated(@NonNull final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // this executes fragment 2 (screens are fragments)
        view.findViewById(R.id.button_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView showCountTextView = view.getRootView().findViewById(R.id.text_random);
                int currentCount = Integer.parseInt(showCountTextView.getText().toString());

                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action =
                        FirstFragmentDirections.
                                actionFirstFragmentToSecondFragment(currentCount);
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(action);
            }
        });

        view.findViewById(R.id.toast_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast myToast = Toast.makeText(getActivity(), R.string.FirstToastText, Toast.LENGTH_SHORT);
                myToast.show();
                Log.e("Test", "Test");
            }
        });

        view.findViewById(R.id.button_count).setOnClickListener(new View.OnClickListener()
             {
                 @Override
                 public void onClick (View view){
                     countMe(view);
                 }
        });

        Switch sw1;
        sw1 = view.findViewById(R.id.switchPopup);

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast myToast = Toast.makeText(getActivity(), "checked", Toast.LENGTH_SHORT);
                    myToast.show();
                    PopUpClass popUpClass = new PopUpClass();
                    popUpClass.showPopupWindow(view);
                } else {
                    Toast myToast = Toast.makeText(getActivity(), "not checked", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

        SeekBar sb1;
        sb1 = view.findViewById(R.id.seekBar);
        sb1.setProgress(0); // init

        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                ImageView iv = view.findViewById(R.id.imageView);

                switch(progress)
                {
                    case 0:
                        iv.setImageResource(ic_launcher_foreground);
                        break;
                    case 1:
                        iv.setImageResource(golem);
                        break;
                    case 2:
                        iv.setImageResource(skel_pime);
                        break;
                    case 3:
                        iv.setImageResource(moonlord_full);
                        break;
                    case 4:
                        iv.setImageResource(ter_boss);
                        break;
                    default:
                        throw new IllegalStateException(getString(R.string.exception) + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private void countMe(View view) {
        String str_count = showCountTextView.getText().toString();
        Integer int_count = Integer.parseInt(str_count);
        int_count++;
        showCountTextView.setText(int_count.toString());
    }
}
