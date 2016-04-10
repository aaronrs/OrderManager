package net.astechdesign.cms.model;

import android.app.Activity;

public class UIValue<T> {

    protected int id;

    public T getValue(Activity cmsActivity) {
        return (T) cmsActivity.findViewById(id);
    }
}
