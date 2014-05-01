package com.socratica.mobile.example.data;

import android.app.Activity;

import com.socratica.mobile.example.BigImageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamplesContent {

    public static List<ExampleItem> ITEMS = new ArrayList<ExampleItem>();

    static {
        addItem(new ExampleItem(BigImageActivity.class, "Big Image"));
    }

    private static void addItem(ExampleItem item) {
        ITEMS.add(item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ExampleItem {
        public Class<? extends Activity>  id;
        public String content;

        public ExampleItem(Class<? extends Activity> id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
