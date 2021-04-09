package com.example.hesnalmuslimclone.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.hesnalmuslimclone.R;
import com.example.hesnalmuslimclone.models.Zekr;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static androidx.core.content.ContextCompat.getSystemService;

public class ThekrUtilsBottomSheet extends BottomSheetDialogFragment {
    String BASE_THEKR_URL = "https://archive.org/download/hesnalmuslimsounds/";
    private static final String TAG = "ThekrUtilsBottomSheet";
    private Zekr thekr;
    private String categoryName;
    private View myView;
    private LinearLayout copyThekrLayout;
    private LinearLayout shareThekrLayout;
    private LinearLayout playThekrLayout;

    public ThekrUtilsBottomSheet(Zekr thekr, String categoryName) {
        this.thekr = thekr;
        this.categoryName = categoryName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.thekr_utils_bottom_sheet, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();

        copyThekrLayout.setOnClickListener(v -> {
            copyToClipboard();
        });


        shareThekrLayout.setOnClickListener(v -> {
            dismiss();
            shareThekr();
        });

        playThekrLayout.setOnClickListener(v -> {
            dismiss();
            if (thekr.categoryId > 27){
                thekr.categoryId -= 1;
            }
            String thekrUrl = BASE_THEKR_URL + thekr.categoryId + ".ogg";
            openAudioBottomSheet(thekrUrl);
        });
    }

    private void copyToClipboard() {
        ClipboardManager clipboard = getSystemService(getContext(), ClipboardManager.class);
        String copiedText = "من "+ categoryName + "\n" + thekr.description + "\n-----\n" + thekr.hint;
        ClipData clip = ClipData.newPlainText("label", copiedText);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(requireContext(), "تم نسخُ الذكرِ بالحافظة", Toast.LENGTH_SHORT).show();
        dismiss();
    }

    private void initViews(){
        copyThekrLayout = myView.findViewById(R.id.copyThekrLayout);
        shareThekrLayout = myView.findViewById(R.id.shareThekrLayout);
        playThekrLayout = myView.findViewById(R.id.playThekrLayout);
    }

    private void openAudioBottomSheet(String audioUrl){
        AudioPlayerBottomSheet audioPlayerBottomSheet = new AudioPlayerBottomSheet(audioUrl);
        audioPlayerBottomSheet.show(getActivity().getSupportFragmentManager(), audioPlayerBottomSheet.getTag());
    }

    private void shareThekr(){
            String messageText = thekr.abstractDescription + "\n\n" + thekr.hint;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, messageText);
            String chooserTitle = getString(R.string.chooser);
            Intent chosenIntent = Intent.createChooser(intent, chooserTitle);
            startActivity(chosenIntent);
    }

}
