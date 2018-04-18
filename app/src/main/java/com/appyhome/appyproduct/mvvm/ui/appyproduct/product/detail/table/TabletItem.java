package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.table;

public class TabletItem {
    public String title;
    public String content;
    public int line;

    public TabletItem(String name,String text, int maxLineNumber) {
        title = name;
        content = text;
        line = maxLineNumber;
    }
}
