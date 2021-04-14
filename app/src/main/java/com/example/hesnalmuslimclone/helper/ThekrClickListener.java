package com.example.hesnalmuslimclone.helper;

import com.example.hesnalmuslimclone.models.Zekr;

public interface ThekrClickListener {
    void onThekrClicked(Zekr zekr, int position);
    void onFinishingAthkar();
    void onThekrLongClicked(Zekr zekr, int position);
}
