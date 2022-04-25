package com.example.notionhelper.view.fragments;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ItemFragment extends Fragment {

    public ItemFragment(int layoutId) {
        super(layoutId);
    }

    public ArrayList<String> getInputs() {
        return new ArrayList<>();
    }

}
