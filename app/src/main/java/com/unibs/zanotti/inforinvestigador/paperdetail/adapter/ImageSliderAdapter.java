package com.unibs.zanotti.inforinvestigador.paperdetail.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.common.collect.Lists;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.unibs.zanotti.inforinvestigador.R;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<Uri> dataset;

    public ImageSliderAdapter(Context context) {
        this.context = context;
        dataset = Lists.newArrayList();
    }

    public List<Uri> getDataset() {
        return dataset;
    }

    public void setDataset(List<Uri> dataset) {
        this.dataset = dataset;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        Picasso.get()
                .load(dataset.get(position))
                .error(R.drawable.paper_image_error_placeholder)
                .fit()
                .centerCrop()
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            this.itemView = itemView;
        }
    }

}
