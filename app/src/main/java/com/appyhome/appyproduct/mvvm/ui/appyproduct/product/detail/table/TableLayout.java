package com.appyhome.appyproduct.mvvm.ui.appyproduct.product.detail.table;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductVariant;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemTableRowBinding;
import com.appyhome.appyproduct.mvvm.databinding.ViewItemTableRowWideBinding;
import com.appyhome.appyproduct.mvvm.ui.common.component.LinearListView;
import com.appyhome.appyproduct.mvvm.utils.helper.DataUtils;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

public class TableLayout extends LinearListView<TabletItem> {

    private int col1Width = 0;

    public TableLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        col1Width = getContext().getResources().getDimensionPixelSize(R.dimen.table_title_col_width);
    }

    public boolean isEnterNewLine(TextView textView, String text) {
        if (textView != null) {
            Rect bounds = new Rect();
            Paint textPaint = textView.getPaint();
            textPaint.getTextBounds(text, 0, text.length(), bounds);
            return bounds.width() > col1Width;
        }
        return false;
    }

    @Override
    public View getItemView(int position) {
        if (position != getItemCount() - 1) {
            TabletItem data = getItem(position);
            ViewItemTableRowBinding binding = ViewItemTableRowBinding.inflate(LayoutInflater.from(getContext()), this, false);
            binding.setData(data);
            if (data.title.length() > data.content.length()) {
                if (isEnterNewLine(binding.titleText, data.title)) {
                    binding.titleText.setLines(2);
                    binding.contentText.setLines(2);
                }
            }
            return binding.getRoot();
        } else {
            ViewItemTableRowWideBinding binding = ViewItemTableRowWideBinding.inflate(LayoutInflater.from(getContext()), this, false);
            binding.setData(getItem(position));
            return binding.getRoot();
        }
    }

    public void loadData(ProductVariant variant) {
        removeAllViews();

        ArrayList<TabletItem> tableAdapter = new ArrayList<>();
        tableAdapter.add(new TabletItem("Name", variant.product_name));
        tableAdapter.add(new TabletItem("Variant Name", variant.variant_name));
        String priceNote = variant.getPriceNote();
        tableAdapter.add(new TabletItem("Price", "RM " + variant.price + (priceNote.length() > 0 ? ("\n" + priceNote) : "")));
        HashMap<String, String> parseDes = variant.parseDescription();
        if (parseDes != null) {
            for (String key : parseDes.keySet()) {
                tableAdapter.add(new TabletItem(key, parseDes.get(key)));
            }
        }
        tableAdapter.add(new TabletItem("Dimension (cm)", variant.getSize()));
        tableAdapter.add(new TabletItem("Weight", DataUtils.roundNumber(variant.weight, 2) + " Kg"));
        tableAdapter.add(new TabletItem("Stock's quantity", variant.quantity + " (" + variant.getAvailable() + ")"));
        String warranty = variant.getWarranty();
        if (warranty.length() > 0)
            tableAdapter.add(new TabletItem("Warranty", warranty));
        tableAdapter.add(new TabletItem("Manufacturer", variant.getManufacturer()));
        tableAdapter.add(new TabletItem("Shipping From", variant.getShippingFrom()));
        tableAdapter.add(new TabletItem("Est. Delivery Date", variant.getDeliveryEstimation()));
        tableAdapter.add(new TabletItem("Payment Methods", "VISA, Master Card, Mol Pay"));
        tableAdapter.add(new TabletItem("Seller Name", variant.seller_name));
        tableAdapter.add(new TabletItem("After Sales Service", "Please contact seller"));
        tableAdapter.add(new TabletItem("Return / Exchange", getContext().getString(R.string.return_notice)));
        tableAdapter.add(new TabletItem("Available Date", "Since " + variant.date_available));
        tableAdapter.add(new TabletItem("Updated Date", variant.updated_at));
        tableAdapter.add(new TabletItem("Description", variant.getDescription()));
        setAdapter(tableAdapter);
    }
}
